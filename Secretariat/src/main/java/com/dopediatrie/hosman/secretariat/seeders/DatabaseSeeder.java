package com.dopediatrie.hosman.secretariat.seeders;

import com.dopediatrie.hosman.secretariat.entity.Acte;
import com.dopediatrie.hosman.secretariat.entity.Groupe;
import com.dopediatrie.hosman.secretariat.payload.request.ActeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.GroupeRequest;
import com.dopediatrie.hosman.secretariat.payload.request.TarifRequest;
import com.dopediatrie.hosman.secretariat.repository.ActeRepository;
import com.dopediatrie.hosman.secretariat.service.ActeService;
import com.dopediatrie.hosman.secretariat.service.GroupeService;
import com.dopediatrie.hosman.secretariat.service.TarifService;
import com.dopediatrie.hosman.secretariat.utils.Str;
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
    private final GroupeService groupeService;
    private final TarifService tarifService;
    private final ActeRepository acteRepository;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, ActeService acteService, GroupeService groupeService, TarifService tarifService, ActeRepository acteRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.acteService = acteService;
        this.groupeService = groupeService;
        this.tarifService = tarifService;
        this.acteRepository = acteRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedGroupeTable();
        seedActeTable();
        seedTarifTable();
    }

    private void seedActeTable() {
        String sql = "SELECT c.libelle FROM acte c";
        List<Acte> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            ActeRequest ar1 = ActeRequest.builder().libelle("Consultations").code("cons").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar2 = ActeRequest.builder().libelle("Actes en K").code("ActeK").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar3 = ActeRequest.builder().libelle("Contrôle").code("Cont").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar4 = ActeRequest.builder().libelle("Vaccinations").code("Vacc").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar5 = ActeRequest.builder().libelle("Injections").code("Vacc").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar6 = ActeRequest.builder().libelle("Pansements").code("Pans").groupe_code("GRP004").structure_id(1).build();
            ActeRequest ar7 = ActeRequest.builder().libelle("Kinésithérapie").code("kine").groupe_code("GRP005").structure_id(1).build();
            ActeRequest ar8 = ActeRequest.builder().libelle("Prise de sang").code("Ps").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar9 = ActeRequest.builder().libelle("Prélèvement Vaginal").code("PV").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar10 = ActeRequest.builder().libelle("IDR").code("idr").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar11 = ActeRequest.builder().libelle("Soins").code("Soin").groupe_code("GRP001").structure_id(1).build();
            ActeRequest ar12 = ActeRequest.builder().libelle("Radiographie").code("radio").groupe_code("GRP008").structure_id(1).build();
            ActeRequest ar13 = ActeRequest.builder().libelle("Echographie").code("echo").groupe_code("GRP006").structure_id(1).build();
            ActeRequest ar14 = ActeRequest.builder().libelle("ECG").code("ecg").groupe_code("GRP010").structure_id(1).build();
            ActeRequest ar15 = ActeRequest.builder().libelle("EEG").code("eeg").groupe_code("GRP011").structure_id(1).build();
            ActeRequest ar16 = ActeRequest.builder().libelle("Scanners").code("scan").groupe_code("GRP002").structure_id(1).build();
            ActeRequest ar17 = ActeRequest.builder().libelle("IRM").code("irm").groupe_code("GRP003").structure_id(1).build();
            ActeRequest ar18 = ActeRequest.builder().libelle("Laboratoire & Analyses").code("ana").groupe_code("GRP012").structure_id(1).build();
            ActeRequest ar19 = ActeRequest.builder().libelle("Endoscopie").code("endo").groupe_code("GRP013").structure_id(1).build();
            ActeRequest ar20 = ActeRequest.builder().libelle("Hémodialyse").code("hemo").groupe_code("GRP014").structure_id(1).build();
            ActeRequest ar21 = ActeRequest.builder().libelle("Médicaments").code("medic").groupe_code("GRP016").structure_id(1).build();
            ActeRequest ar22 = ActeRequest.builder().libelle("Consommables").code("conso").groupe_code("GRP017").structure_id(1).build();
            ActeRequest ar23 = ActeRequest.builder().libelle("Kits").code("kit").groupe_code("GRP015").structure_id(1).build();
            ActeRequest ar24 = ActeRequest.builder().libelle("Autres").code("Autres").groupe_code("GRP001").structure_id(1).build();

            acteService.addActe(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10, ar11, ar12, ar13, ar14, ar15, ar16, ar17, ar18, ar19, ar20, ar21, ar22, ar23, ar24));

            log.info("actes table seeded");
        }else {
            log.info("Acte Seeding Not Required");
        }
    }

    private void seedGroupeTable() {
        String sql = "SELECT c.libelle FROM groupe c";
        List<Groupe> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            GroupeRequest ar1 = GroupeRequest.builder().libelle("Actes Médicaux").code("GRP001").position(1).couleur("white").structure_id(1).build();
            GroupeRequest ar4 = GroupeRequest.builder().libelle("Scanners").code("GRP002").position(4).couleur("white").structure_id(1).build();
            GroupeRequest ar5 = GroupeRequest.builder().libelle("IRM").code("GRP003").position(5).couleur("white").structure_id(1).build();
            GroupeRequest ar6 = GroupeRequest.builder().libelle("Pansements").code("GRP004").position(10).couleur("white").structure_id(1).build();
            GroupeRequest ar7 = GroupeRequest.builder().libelle("Kinésithérapie").code("GRP005").position(9).couleur("white").structure_id(1).build();
            GroupeRequest ar8 = GroupeRequest.builder().libelle("Injections").code("GRP006").position(11).couleur("white").structure_id(1).build();
            GroupeRequest ar10 = GroupeRequest.builder().libelle("IDR").code("GRP007").position(9).couleur("white").structure_id(1).build();
            GroupeRequest ar12 = GroupeRequest.builder().libelle("Radiographie").code("GRP008").position(3).couleur("white").structure_id(1).build();
            GroupeRequest ar13 = GroupeRequest.builder().libelle("Echographie").code("GRP009").position(6).couleur("white").structure_id(1).build();
            GroupeRequest ar14 = GroupeRequest.builder().libelle("ECG").code("GRP010").position(7).couleur("white").structure_id(1).build();
            GroupeRequest ar15 = GroupeRequest.builder().libelle("EEG").code("GRP011").position(8).couleur("white").structure_id(1).build();
            GroupeRequest ar18 = GroupeRequest.builder().libelle("Analyses").code("GRP012").position(2).couleur("white").structure_id(1).build();
            GroupeRequest ar19 = GroupeRequest.builder().libelle("Endoscopie").code("GRP013").position(12).couleur("white").structure_id(1).build();
            GroupeRequest ar20 = GroupeRequest.builder().libelle("Hémodialyse").code("GRP014").position(13).couleur("white").structure_id(1).build();
            GroupeRequest ar23 = GroupeRequest.builder().libelle("Kits").code("GRP015").position(16).couleur("white").structure_id(1).build();
            GroupeRequest ar24 = GroupeRequest.builder().libelle("Médicaments").code("GRP016").position(16).couleur("white").structure_id(1).build();
            GroupeRequest ar25 = GroupeRequest.builder().libelle("Consommables").code("GRP017").position(16).couleur("white").structure_id(1).build();

            groupeService.addGroupe(Arrays.asList(ar1, ar4, ar5, ar6, ar7, ar8, ar10, ar12, ar13, ar14, ar15, ar18, ar19, ar20, ar23, ar24, ar25));

            log.info("Groupe table seeded");
        }else {
            log.info("Groupe Seeding Not Required");
        }
    }

    private void seedTarifTable() {
        String sql = "SELECT structure_id FROM tarif";
        List<Acte> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<TarifRequest> tarifRequests = new ArrayList<TarifRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/secretariat/tarifs.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(";");
                TarifRequest sar = TarifRequest.builder()
                        .libelle(cols[5])
                        .slug(Str.slug(cols[5]))
                        .code(cols[0])
                        .description(cols[6])
                        .tarif_non_assure(Double.parseDouble(cols[1]))
                        .tarif_assur_locale(Double.parseDouble(cols[2]))
                        .tarif_etr_non_assure(Double.parseDouble(cols[3]))
                        .tarif_assur_hors_zone(Double.parseDouble(cols[4]))
                        .acte_id(acteRepository.findById(Long.parseLong(cols[7])).orElseThrow().getId())
                        .structure_id(1)
                        .build();
                tarifRequests.add(sar);
            });

            tarifService.addTarif(tarifRequests);

            log.info("Tarif table seeded");
        }else {
            log.info("Tarif Seeding Not Required");
        }
    }
}
