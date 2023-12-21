package com.dopediatrie.hosman.hospi.seeder;

import com.dopediatrie.hosman.hospi.entity.Chambre;
import com.dopediatrie.hosman.hospi.entity.Lit;
import com.dopediatrie.hosman.hospi.payload.request.ChambreRequest;
import com.dopediatrie.hosman.hospi.payload.request.LitRequest;
import com.dopediatrie.hosman.hospi.service.ChambreService;
import com.dopediatrie.hosman.hospi.service.LitService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Component
@Log4j2
public class DatabaseSeeder {
    private JdbcTemplate jdbcTemplate;

    private final ChambreService chambreService;
    private final LitService litService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, ChambreService chambreService, LitService litService) {
        this.jdbcTemplate = jdbcTemplate;
        this.chambreService = chambreService;
        this.litService = litService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedChambreTable();
        seedLitTable();
    }

    private void seedLitTable() {
        String sql = "SELECT c.nom FROM lit c";
        List<Lit> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            LitRequest ar1 = LitRequest.builder().nom("001").chambre_id(1).build();
            LitRequest ar2 = LitRequest.builder().nom("002").chambre_id(1).build();
            LitRequest ar3 = LitRequest.builder().nom("003").chambre_id(1).build();
            LitRequest ar4 = LitRequest.builder().nom("Face porte").chambre_id(2).build();
            LitRequest ar5 = LitRequest.builder().nom("Face fenêtre").chambre_id(2).build();
            LitRequest ar6 = LitRequest.builder().nom("Centre").chambre_id(3).build();
            LitRequest ar7 = LitRequest.builder().nom("Centre unique").chambre_id(3).build();
            LitRequest ar8 = LitRequest.builder().nom("Face porte").chambre_id(3).build();
            LitRequest ar9 = LitRequest.builder().nom("Face fenêtre").chambre_id(4).build();
            LitRequest ar10 = LitRequest.builder().nom("001").chambre_id(4).build();
            LitRequest ar11 = LitRequest.builder().nom("002").chambre_id(4).build();
            LitRequest ar12 = LitRequest.builder().nom("003").chambre_id(4).build();
            LitRequest ar13 = LitRequest.builder().nom("001").chambre_id(5).build();
            LitRequest ar14 = LitRequest.builder().nom("002").chambre_id(5).build();
            LitRequest ar15 = LitRequest.builder().nom("001").chambre_id(6).build();
            LitRequest ar16 = LitRequest.builder().nom("002").chambre_id(6).build();
            LitRequest ar17 = LitRequest.builder().nom("Centre").chambre_id(7).build();
            LitRequest ar18 = LitRequest.builder().nom("Centre").chambre_id(8).build();
            LitRequest ar19 = LitRequest.builder().nom("Centre unique").chambre_id(9).build();
            LitRequest ar20 = LitRequest.builder().nom("Face fenêtre").chambre_id(9).build();
            LitRequest ar21 = LitRequest.builder().nom("Face fenêtre").chambre_id(10).build();
            LitRequest ar22 = LitRequest.builder().nom("Face porte").chambre_id(11).build();
            LitRequest ar23 = LitRequest.builder().nom("Face porte").chambre_id(12).build();
            LitRequest ar24 = LitRequest.builder().nom("Face porte").chambre_id(13).build();

            litService.addLit(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10, ar11, ar12, ar13, ar14, ar15, ar16, ar17, ar18, ar19, ar20, ar21, ar22, ar23, ar24));

            log.info("lits table seeded");
        }else {
            log.info("Lit Seeding Not Required");
        }
    }

    private void seedChambreTable() {
        String sql = "SELECT c.nom FROM chambre c";
        List<Chambre> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            ChambreRequest ar1 = ChambreRequest.builder().nom("Annexe 1").build();
            ChambreRequest ar4 = ChambreRequest.builder().nom("Annexe 2").build();
            ChambreRequest ar5 = ChambreRequest.builder().nom("Réa 1").build();
            ChambreRequest ar6 = ChambreRequest.builder().nom("Réa 2").build();
            ChambreRequest ar7 = ChambreRequest.builder().nom("Chambre 102").build();
            ChambreRequest ar8 = ChambreRequest.builder().nom("Chambre 103").build();
            ChambreRequest ar10 = ChambreRequest.builder().nom("Chambre 104").build();
            ChambreRequest ar12 = ChambreRequest.builder().nom("Chambre 105").build();
            ChambreRequest ar13 = ChambreRequest.builder().nom("Chambre 200").build();
            ChambreRequest ar14 = ChambreRequest.builder().nom("Chambre 201").build();
            ChambreRequest ar15 = ChambreRequest.builder().nom("Chambre 202").build();
            ChambreRequest ar18 = ChambreRequest.builder().nom("Chambre 203").build();
            ChambreRequest ar19 = ChambreRequest.builder().nom("Chambre 204").build();
            ChambreRequest ar20 = ChambreRequest.builder().nom("Chambre 205").build();
            ChambreRequest ar23 = ChambreRequest.builder().nom("Chambre 301").build();
            ChambreRequest ar24 = ChambreRequest.builder().nom("Chambre 302").build();
            ChambreRequest ar25 = ChambreRequest.builder().nom("Chambre 303").build();

            chambreService.addChambre(Arrays.asList(ar1, ar4, ar5, ar6, ar7, ar8, ar10, ar12, ar13, ar14, ar15, ar18, ar19, ar20, ar23, ar24, ar25));

            log.info("Chambre table seeded");
        }else {
            log.info("Chambre Seeding Not Required");
        }
    }

}
