package com.dopediatrie.hosman.auth.seeder;

import com.dopediatrie.hosman.auth.entity.*;
import com.dopediatrie.hosman.auth.payload.request.*;
import com.dopediatrie.hosman.auth.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
public class DatabaseSeeder {
    private JdbcTemplate jdbcTemplate;

    private final StructureService structureService;
    private final DepartementService departementService;
    private final SecteurService secteurService;
    private final PosteService posteService;
    private final EmployeService employeService;
    private final UserService userService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, StructureService structureService, DepartementService departementService, SecteurService secteurService, PosteService posteService,
                          EmployeService employeService, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.structureService = structureService;
        this.departementService = departementService;
        this.posteService = posteService;
        this.employeService = employeService;
        this.userService = userService;
        this.secteurService = secteurService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedStructureTable();
        seedDepartementTable();
        seedSecteurTable();
        seedPosteTable();
        seedEmployeTable();
        seedUserTable();
    }

    private void seedStructureTable() {
        String sql = "SELECT c.nom FROM structure c";
        List<Structure> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {

            String structName = "POLYCLINIQUE INTERNATIONALE SAINT JOSEPH";
            StructureRequest ar1 = StructureRequest.builder()
                    .nom(structName)
                    .raison_sociale(structName)
                    .email("pisjo@hosman-apps.com")
                    .adresse("hedzranawé, Lomé, Togo.")
                    .tel1("22 26 72 32")
                    .tel2("22 29 94 32")
                    .bp("10039")
                    .fax("22 29 79 39")
                    .sigle("PISJO")
                    .created_at(LocalDateTime.now())
                    .build();

            structureService.addStructure(Arrays.asList(ar1));

            log.info("Structure table seeded");
        }else {
            log.info("Structure Seeding Not Required");
        }
    }

    private void seedDepartementTable() {
        String sql = "SELECT c.libelle FROM departement c";
        List<Departement> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {

            DepartementRequest ar1 = DepartementRequest.builder().libelle("Directeur Générale").couleur("bleu").code("DG").structure_id(1).build();
            DepartementRequest ar2 = DepartementRequest.builder().libelle("Direction des Ressources humaines").couleur("bleu").code("DRH").structure_id(1).build();
            DepartementRequest ar3 = DepartementRequest.builder().libelle("Direction des Affaires Financières").couleur("bleu").code("DAF").structure_id(1).build();
            DepartementRequest ar4 = DepartementRequest.builder().libelle("Direction des Soins").couleur("bleu").code("DS").structure_id(1).build();

            departementService.addDepartement(Arrays.asList(ar1, ar2, ar3, ar4));

            log.info("Departement table seeded");
        }else {
            log.info("Departement Seeding Not Required");
        }
    }

    private void seedSecteurTable() {
        String sql = "SELECT c.libelle FROM secteur c";
        List<Secteur> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {

            SecteurRequest ar1 = SecteurRequest.builder().libelle("Médecine Interne et Générale").couleur("bleu").code("MIG").departement("DS").build();
            SecteurRequest ar2 = SecteurRequest.builder().libelle("Pédiatrie").couleur("bleu").code("PDI").departement("DS").build();
            SecteurRequest ar3 = SecteurRequest.builder().libelle("Cardiologie").couleur("bleu").code("CAR").departement("DS").build();
            SecteurRequest ar4 = SecteurRequest.builder().libelle("Neurologie").couleur("bleu").code("NER").departement("DS").build();
            SecteurRequest ar5 = SecteurRequest.builder().libelle("Ophtalmologie").couleur("bleu").code("OPH").departement("DS").build();
            SecteurRequest ar6 = SecteurRequest.builder().libelle("Sécrétariat").couleur("bleu").code("SCR").departement("DAF").build();
            SecteurRequest ar7 = SecteurRequest.builder().libelle("Comptabilité").couleur("bleu").code("COM").departement("DAF").build();
            SecteurRequest ar8 = SecteurRequest.builder().libelle("Ressources Humaines").couleur("bleu").code("RH").departement("DRH").build();
            SecteurRequest ar9 = SecteurRequest.builder().libelle("Logistique").couleur("bleu").code("LOG").departement("DS").build();
            SecteurRequest ar10 = SecteurRequest.builder().libelle("Gynécologie").couleur("bleu").code("GYN").departement("DS").build();
            SecteurRequest ar11 = SecteurRequest.builder().libelle("Maternité").couleur("bleu").code("MAT").departement("DS").build();

            secteurService.addSecteur(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10, ar11));

            log.info("Secteur table seeded");
        }else {
            log.info("Secteur Seeding Not Required");
        }
    }

    private void seedPosteTable() {
        String sql = "SELECT c.intitule FROM poste c";
        List<Poste> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {

            PosteRequest ar1 = PosteRequest.builder().intitule("Secrétaire").code("SCR").build();
            PosteRequest ar2 = PosteRequest.builder().intitule("Medecin généraliste").code("MG").build();
            PosteRequest ar3 = PosteRequest.builder().intitule("Médecin Interne").code("MI").build();
            PosteRequest ar4 = PosteRequest.builder().intitule("Médecin Spécialiste").code("MS").build();
            PosteRequest ar5 = PosteRequest.builder().intitule("Neuro-chirurgien").code("NER").build();
            PosteRequest ar6 = PosteRequest.builder().intitule("Chirurgien").code("CHR").build();
            PosteRequest ar7 = PosteRequest.builder().intitule("DRH").code("DRH").build();
            PosteRequest ar8 = PosteRequest.builder().intitule("Infirmier").code("INF").build();
            PosteRequest ar9 = PosteRequest.builder().intitule("Infirmière").code("INFe").build();
            PosteRequest ar10 = PosteRequest.builder().intitule("Pédiatre").code("PDI").build();
            PosteRequest ar11 = PosteRequest.builder().intitule("Agent d'entretien").code("AE").build();
            PosteRequest ar12 = PosteRequest.builder().intitule("Comptable").code("COM").build();

            posteService.addPoste(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10, ar11, ar12));

            log.info("Poste table seeded");
        }else {
            log.info("Poste Seeding Not Required");
        }
    }

    private void seedEmployeTable() {
        String sql = "SELECT c.matricule FROM employe c";
        List<Employe> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {

            EmployePosteRequest epr1 = EmployePosteRequest.builder().employe_matricule("PISJO001").poste_code("PDI").build();
            EmployePosteRequest epr2 = EmployePosteRequest.builder().employe_matricule("PISJO001").poste_code("SCR").build();
            EmployePosteRequest epr3 = EmployePosteRequest.builder().employe_matricule("PISJO002").poste_code("MI").build();
            EmployePosteRequest epr4 = EmployePosteRequest.builder().employe_matricule("PISJO002").poste_code("MS").build();
            EmployeRequest ar1 = EmployeRequest.builder()
                    .matricule("PISJO001")
                    .nom("DOVI-AKUE")
                    .prenoms("J-P")
                    .sexe('M')
                    .tel1("90991898")
                    .email("sylcam@hotmail.fr")
                    .adresse("Hédzranawé, Lomé, Togo.")
                    .date_debut(LocalDateTime.now())
                    .is_employe(true)
                    .is_temporaire(false)
                    .secteur("PDI")
                    .structure_id(1)
                    .profession_id(1)
                    .nationalite_id(1)
                    .postes(Arrays.asList(epr1, epr2))
                    .build();
            EmployeRequest ar2 = EmployeRequest.builder()
                    .matricule("PISJO002")
                    .nom("ABALO")
                    .prenoms("Serein")
                    .sexe('M')
                    .tel1("99821564")
                    .email("sabalo@hotmail.fr")
                    .adresse("Hédzranawé, Lomé, Togo.")
                    .date_debut(LocalDateTime.now())
                    .is_employe(true)
                    .is_temporaire(false)
                    .secteur("MIG")
                    .structure_id(1)
                    .profession_id(1)
                    .nationalite_id(1)
                    .postes(Arrays.asList(epr3, epr4))
                    .build();

            employeService.addEmploye(Arrays.asList(ar1, ar2));

            log.info("Employe table seeded");
        }else {
            log.info("Employe Seeding Not Required");
        }
    }

    private void seedUserTable() {
        String sql = "SELECT c.username FROM `user` c";
        List<User> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {

            UserRequest ar1 = UserRequest.builder().username("jpdovi").password("password").is_active(true).created_at(LocalDateTime.now()).employe_matricule("PISJO001").build();

            userService.addUser(Arrays.asList(ar1));

            log.info("User table seeded");
        }else {
            log.info("User Seeding Not Required");
        }
    }
}
