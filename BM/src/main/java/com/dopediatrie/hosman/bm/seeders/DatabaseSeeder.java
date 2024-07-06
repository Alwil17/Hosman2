package com.dopediatrie.hosman.bm.seeders;

import com.dopediatrie.hosman.bm.entity.*;
import com.dopediatrie.hosman.bm.payload.ICDAPIclient;
import com.dopediatrie.hosman.bm.payload.request.*;
import com.dopediatrie.hosman.bm.service.*;
import com.dopediatrie.hosman.bm.utils.Str;
import com.dopediatrie.hosman.bm.utils.Utils;
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
import java.util.List;
import java.util.stream.Stream;

@Component
@Log4j2
public class DatabaseSeeder {
    private JdbcTemplate jdbcTemplate;

    private final MotifService motifService;
    private final AgenceService agenceService;
    private final LaboratoireService laboratoireService;
    private final DelegueService delegueService;
    private final ClasseService classeService;
    private final ProduitService produitService;
    private final HelpersService helpersService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, MotifService motifService, AgenceService agenceService,
                          LaboratoireService laboratoireService, DelegueService delegueService, ClasseService classeService,
                          ProduitService produitService, HelpersService helpersService) {
        this.jdbcTemplate = jdbcTemplate;
        this.motifService = motifService;
        this.agenceService = agenceService;
        this.laboratoireService = laboratoireService;
        this.delegueService = delegueService;
        this.classeService = classeService;
        this.produitService = produitService;
        this.helpersService = helpersService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedMotifTable();
        seedAgenceTable();
        seedLaboratoireTable();
        seedDelegueTable();
        seedClasseTable();
        seedProduitTable();
        seedHelpersTable();
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

    private void seedAgenceTable() {
        String sql = "SELECT nom FROM agence";
        List<Agence> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<AgenceRequest> agenceRequests = new ArrayList<AgenceRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/agences.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(",");
                //log.info(cols[4]);
                AgenceRequest sar = AgenceRequest.builder()
                        .nom(cols[0])
                        .directeur(cols[1])
                        .email(cols[2])
                        .tel1(cols[3])
                        .tel2(cols[4])
                        .adresse(cols[5])
                        .build();
                agenceRequests.add(sar);
            });

            agenceService.addAgence(agenceRequests);

            log.info("Agence table seeded");
        }else {
            /*try {
            String uri = "https://id.who.int/icd/entity";
                ICDAPIclient api = new ICDAPIclient();
                String token = api.getToken();
                System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            log.info("Agence Seeding Not Required");
        }
    }

    private void seedLaboratoireTable() {
        String sql = "SELECT nom FROM laboratoire";
        List<Laboratoire> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<LaboratoireRequest> laboRequests = new ArrayList<LaboratoireRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/laboratoires.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(",");
                //log.info(cols[4]);
                LaboratoireRequest sar = LaboratoireRequest.builder()
                        .agence((AgenceRequest.builder().nom(cols[0]).build()))
                        .nom(cols[3])
                        .email(cols[4])
                        .tel1(cols[5])
                        .tel2(cols[6])
                        .adresse(cols[7])
                        .build();
                laboRequests.add(sar);
            });

            laboratoireService.addLaboratoire(laboRequests);

            log.info("Laboratoire table seeded");
        }else {
            /*try {
            String uri = "https://id.who.int/icd/entity";
                ICDAPIclient api = new ICDAPIclient();
                String token = api.getToken();
                System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            log.info("Laboratoire Seeding Not Required");
        }
    }

    private void seedDelegueTable() {
        String sql = "SELECT nom FROM delegue";
        List<Delegue> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<DelegueRequest> delegueRequests = new ArrayList<DelegueRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/delegues.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(",");
                //log.info(cols[4]);
                DelegueRequest sar = DelegueRequest.builder()
                        .laboratoire(LaboratoireRequest.builder().nom(cols[0]).build())
                        .nom(cols[2])
                        .prenoms(cols[3])
                        .tel1(cols[4])
                        .tel2(cols[5])
                        .email(cols[6])
                        .adresse(cols[7])
                        .build();
                delegueRequests.add(sar);
            });

            delegueService.addDelegue(delegueRequests);

            log.info("Delegue table seeded");
        }else {
            /*try {
            String uri = "https://id.who.int/icd/entity";
                ICDAPIclient api = new ICDAPIclient();
                String token = api.getToken();
                System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            log.info("Delegue Seeding Not Required");
        }
    }

    private void seedClasseTable() {
        String sql = "SELECT nom FROM classe";
        List<Classe> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<ClasseRequest> classeRequests = new ArrayList<ClasseRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/classes.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(",");
                //log.info(cols[4]);
                ClasseRequest sar = ClasseRequest.builder()
                        .nom(cols[0])
                        .couleur(cols[1])
                        .build();
                classeRequests.add(sar);
            });

            classeService.addClasse(classeRequests);

            log.info("Classe table seeded");
        }else {
            /*try {
            String uri = "https://id.who.int/icd/entity";
                ICDAPIclient api = new ICDAPIclient();
                String token = api.getToken();
                System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            log.info("Classe Seeding Not Required");
        }
    }

    private void seedProduitTable() {
        String sql = "SELECT nom FROM produit";
        List<Produit> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<ProduitRequest> produitRequests = new ArrayList<ProduitRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/produits.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(";");
                //log.info(cols[4]);
                AgenceRequest agenceRequest = AgenceRequest.builder().nom(cols[0]).build();
                LaboratoireRequest laboratoireRequest = LaboratoireRequest.builder()
                        .agence(agenceRequest)
                        .nom(cols[1]).build();
                DelegueRequest delegueRequest = DelegueRequest.builder()
                        .laboratoire(laboratoireRequest)
                        .nom(cols[2])
                        .prenoms(cols[3])
                        .tel1(cols[4])
                        .tel2(cols[5])
                        .email(cols[6])
                        .build();
                ClasseRequest classeRequest = ClasseRequest.builder()
                        .nom(cols[7]).build();
                List<ClasseRequest> classeRequests = new ArrayList<>();
                classeRequests.add(classeRequest);
                List<NameRequest> indications = Utils.turnToList(cols[10]);
                List<NameRequest> contreIndications = Utils.turnToList(cols[11]);
                List<NameRequest> effets = Utils.turnToList(cols[12]);
                List<PosologieRequest> poso_adulte = Utils.turnToListP(cols[13], "adulte");
                List<PosologieRequest> poso_enfant = Utils.turnToListP(cols[14], "enfant");
                List<PosologieRequest> posologieRequests = new ArrayList<PosologieRequest>();
                posologieRequests.addAll(poso_adulte);
                posologieRequests.addAll(poso_enfant);
                List<FormeRequest> formes = Utils.turnToListF(cols[15]);
                ProduitRequest sar = ProduitRequest.builder()
                        .agence(agenceRequest)
                        .laboratoire(laboratoireRequest)
                        .delegue(delegueRequest)
                        .classes(classeRequests)
                        .nom(cols[8])
                        .dci(cols[9])
                        .indications(indications)
                        .contre_indications(contreIndications)
                        .effet_secondaires(effets)
                        .posologies(posologieRequests)
                        .formes(formes)
                        .infos(cols[16])
                        .build();
                produitRequests.add(sar);
            });

            produitService.addProduit(produitRequests);

            log.info("Produit table seeded");
        }else {
            /*try {
            String uri = "https://id.who.int/icd/entity";
                ICDAPIclient api = new ICDAPIclient();
                String token = api.getToken();
                System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            log.info("Produit Seeding Not Required");
        }
    }

    private void seedHelpersTable() {
        String sql = "SELECT a.type FROM helpers a where a.type = 'dose'";
        List<Helpers> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<Helpers> helpersRequests = new ArrayList<Helpers>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/liste.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(",");
                Helpers h = Helpers.builder().build();
                if (cols.length >= 5){
                    h.setType("dose");
                    h.setContent(cols[4]);
                    h.setSlug(Str.slug(cols[4]));
                }
                if (cols.length >= 6){
                    h.setType("periode");
                    h.setContent(cols[5]);
                    h.setSlug(Str.slug(cols[5]));
                }
                if (cols.length >= 7){
                    h.setType("adverbe");
                    h.setContent(cols[6]);
                    h.setSlug(Str.slug(cols[6]));
                }
                if (cols.length >= 9){
                    h.setType("duree");
                    h.setContent(cols[8]);
                    h.setSlug(Str.slug(cols[8]));
                }
                if(h.getType() != null && !h.getType().isBlank()) helpersRequests.add(h);
            });

            if(helpersRequests.size() > 0) helpersService.addHelpers(helpersRequests);

            log.info("Helpers table seeded");
        }else {
            /*try {
            String uri = "https://id.who.int/icd/entity";
                ICDAPIclient api = new ICDAPIclient();
                String token = api.getToken();
                System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            log.info("Helpers Seeding Not Required");
        }
    }
}
