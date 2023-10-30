package com.dopediatrie.hosman.stock.seeders;

import com.dopediatrie.hosman.stock.entity.Produit;
import com.dopediatrie.hosman.stock.payload.request.ProduitRequest;
import com.dopediatrie.hosman.stock.service.ProduitService;
import com.dopediatrie.hosman.stock.utils.Str;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@Log4j2
public class DatabaseSeeder {
    private JdbcTemplate jdbcTemplate;

    private final ProduitService produitService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, ProduitService produitService) {
        this.jdbcTemplate = jdbcTemplate;
        this.produitService = produitService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedProduitTable();
    }

    private void seedProduitTable() {
        String sql = "SELECT nom FROM produit";
        List<Produit> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<ProduitRequest> produitRequests = new ArrayList<ProduitRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/stock/produits.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(";");
                //log.info(cols[4]);
                ProduitRequest sar = ProduitRequest.builder()
                        .nom(cols[1])
                        .code(cols[0])
                        .code_acte(cols[3])
                        .nom_officiel(cols[2])
                        .prix((cols.length >= 5) ? Double.parseDouble(cols[4]) : 0)
                        .build();
                produitRequests.add(sar);
            });

            produitService.addProduit(produitRequests);

            log.info("Produit table seeded");
        }else {
            log.info("Produit Seeding Not Required");
        }
    }
}
