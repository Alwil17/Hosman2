package com.dopediatrie.hosman.secretariat.seeders;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.payload.request.ActeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.SousActeRequest;
import com.dopediatrie.hosman.secretariat.service.ActeService;
import com.dopediatrie.hosman.secretariat.service.SousActeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Component
@Log4j2
public class DatabaseSeeder {
    private JdbcTemplate jdbcTemplate;

    private final ActeService acteService;
    private final SousActeService sousActeService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, ActeService acteService, SousActeService sousActeService) {
        this.jdbcTemplate = jdbcTemplate;
        this.acteService = acteService;
        this.sousActeService = sousActeService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedActeTable();
        seedSousActeTable();
    }

    private void seedActeTable() {
        String sql = "SELECT c.libelle FROM acte c";
        List<Acte> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            ActeRequest ar1 = ActeRequest.builder().libelle("Consultations").code("cons").position(0).structure_id(1).build();
            ActeRequest ar2 = ActeRequest.builder().libelle("Actes en K").code("ActeK").position(1).structure_id(1).build();
            ActeRequest ar3 = ActeRequest.builder().libelle("Contrôle").code("Cont").position(2).structure_id(1).build();
            ActeRequest ar4 = ActeRequest.builder().libelle("Vaccinations").code("Vacc").position(3).structure_id(1).build();
            ActeRequest ar5 = ActeRequest.builder().libelle("Injections").code("Vacc").position(4).structure_id(1).build();
            ActeRequest ar6 = ActeRequest.builder().libelle("Pansements").code("Pans").position(5).structure_id(1).build();
            ActeRequest ar7 = ActeRequest.builder().libelle("Kinésithérapie").code("kine").position(6).structure_id(1).build();
            ActeRequest ar8 = ActeRequest.builder().libelle("Prise de sang").code("Ps").position(7).structure_id(1).build();
            ActeRequest ar9 = ActeRequest.builder().libelle("Prélèvement Vaginal").code("PV").position(8).structure_id(1).build();
            ActeRequest ar10 = ActeRequest.builder().libelle("IDR").code("idr").position(9).structure_id(1).build();
            ActeRequest ar11 = ActeRequest.builder().libelle("Soins").code("Soin").position(10).structure_id(1).build();
            ActeRequest ar12 = ActeRequest.builder().libelle("Radiographie").code("radio").position(11).structure_id(1).build();
            ActeRequest ar13 = ActeRequest.builder().libelle("Echographie").code("echo").position(12).structure_id(1).build();
            ActeRequest ar14 = ActeRequest.builder().libelle("ECG").code("ecg").position(13).structure_id(1).build();
            ActeRequest ar15 = ActeRequest.builder().libelle("EEG").code("eeg").position(14).structure_id(1).build();
            ActeRequest ar16 = ActeRequest.builder().libelle("Scanners").code("scan").position(15).structure_id(1).build();
            ActeRequest ar17 = ActeRequest.builder().libelle("IRM").code("irm").position(16).structure_id(1).build();
            ActeRequest ar18 = ActeRequest.builder().libelle("Laboratoire & Analyses").code("ana").position(17).structure_id(1).build();
            ActeRequest ar19 = ActeRequest.builder().libelle("Endoscopie").code("endo").position(18).structure_id(1).build();
            ActeRequest ar20 = ActeRequest.builder().libelle("Hémodialyse").code("hemo").position(19).structure_id(1).build();
            ActeRequest ar21 = ActeRequest.builder().libelle("Médicaments").code("medic").position(20).structure_id(1).build();
            ActeRequest ar22 = ActeRequest.builder().libelle("Consommables").code("conso").position(21).structure_id(1).build();
            ActeRequest ar23 = ActeRequest.builder().libelle("Kits").code("kit").position(22).structure_id(1).build();
            ActeRequest ar24 = ActeRequest.builder().libelle("Autres").code("Autres").position(23).structure_id(1).build();

            acteService.addActe(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10, ar11, ar12, ar13, ar14, ar15, ar16, ar17, ar18, ar19, ar20, ar21, ar22, ar23, ar24));

            log.info("actes table seeded");
        }else {
            log.info("Acte Seeding Not Required");
        }
    }


    private void seedSousActeTable() {
        String sql = "SELECT c.libelle FROM sous_acte c";
        List<Acte> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<SousActeRequest> sousActeRequests = new ArrayList<SousActeRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/secretariat/sous_actes.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(";");
                SousActeRequest sar = SousActeRequest.builder()
                        .code(cols[0])
                        .libelle(cols[1])
                        .description(cols[2])
                        .acte_id(Integer.parseInt(cols[3]))
                        .build();
                sousActeRequests.add(sar);
            });

            sousActeService.addSousActe(sousActeRequests);

            log.info("SousActe table seeded");
        }else {
            log.info("SousActe Seeding Not Required");
        }
    }
}
