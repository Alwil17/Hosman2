package com.dopediatrie.hosman.bm.seeders;

import com.dopediatrie.hosman.bm.entity.Motif;
import com.dopediatrie.hosman.bm.payload.ICDAPIclient;
import com.dopediatrie.hosman.bm.payload.request.MotifRequest;
import com.dopediatrie.hosman.bm.service.MotifService;
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

    private final MotifService motifService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, MotifService motifService) {
        this.jdbcTemplate = jdbcTemplate;
        this.motifService = motifService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedMotifTable();
    }

    private void seedMotifTable() {
        String sql = "SELECT libelle FROM motif";
        List<Motif> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<MotifRequest> motifRequests = new ArrayList<MotifRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/motifs.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(";");
                //log.info(cols[4]);
                MotifRequest sar = MotifRequest.builder()
                        .libelle(cols[0])
                        .build();
                motifRequests.add(sar);
            });

            motifService.addMotif(motifRequests);

            log.info("Motif table seeded");
        }else {
            /*try {
            String uri = "https://id.who.int/icd/entity";
                ICDAPIclient api = new ICDAPIclient();
                String token = api.getToken();
                System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            log.info("Motif Seeding Not Required");
        }
    }
}
