package com.dopediatrie.hosman.secretariat.seeders;

import com.dopediatrie.hosman.secretariat.entity.*;
import com.dopediatrie.hosman.secretariat.payload.request.*;
import com.dopediatrie.hosman.secretariat.repository.ActeRepository;
import com.dopediatrie.hosman.secretariat.service.*;
import com.dopediatrie.hosman.secretariat.utils.Str;
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
import java.util.*;
import java.util.stream.Stream;

@Component
@Log4j2
public class DatabaseSeeder {
    private JdbcTemplate jdbcTemplate;

    private final ActeService acteService;
    private final GroupeService groupeService;
    private final TarifService tarifService;
    private final ActeRepository acteRepository;
    private final PaysService paysService;
    private final VilleService villeService;
    private final QuartierService quartierService;
    private final ProfessionService professionService;
    private final EmployeurService employeurService;
    private final TypeAssuranceService typeAssuranceService;
    private final ModePayementService modePayementService;
    private final EtatService etatService;
    private final DeviseService deviseService;
    private final AssuranceService assuranceService;
    private final CategorieService categorieService;

    @Autowired
    public DatabaseSeeder(JdbcTemplate jdbcTemplate, ActeService acteService, GroupeService groupeService, ActeRepository acteRepository, TarifService tarifService,
                          PaysService paysService, VilleService villeService, QuartierService quartierService, ProfessionService professionService,
                          EmployeurService employeurService, TypeAssuranceService typeAssuranceService, ModePayementService modePayementService,
                          EtatService etatService, DeviseService deviseService, AssuranceService assuranceService, CategorieService categorieService) {
        this.jdbcTemplate = jdbcTemplate;
        this.acteService = acteService;
        this.acteRepository = acteRepository;
        this.groupeService = groupeService;
        this.tarifService = tarifService;
        this.paysService = paysService;
        this.villeService = villeService;
        this.quartierService = quartierService;
        this.professionService = professionService;
        this.employeurService = employeurService;
        this.typeAssuranceService = typeAssuranceService;
        this.modePayementService = modePayementService;
        this.etatService = etatService;
        this.deviseService = deviseService;
        this.assuranceService = assuranceService;
        this.categorieService = categorieService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedGroupeTable();
        seedActeTable();
        seedTarifTable();
        seedPaysTable();
        seedVilleTable();
        seedQuartierTable();
        seedProfessionTable();
        seedEmployeurTable();
        seedTypeAssuranceTable();
        seedModePayementTable();
        seedEtatTable();
        seedDeviseTable();
        seedAssuranceTable();
        seedCategorieTable();
    }

    private void seedActeTable() {
        String sql = "SELECT c.libelle FROM acte c";
        List<Acte> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            ActeRequest ar1 = ActeRequest.builder().libelle("Consultations").code("cons").groupe_code("GRP001").position(0).structure_id(1).build();
            ActeRequest ar2 = ActeRequest.builder().libelle("Actes en K").code("ActeK").groupe_code("GRP001").position(2).structure_id(1).build();
            ActeRequest ar3 = ActeRequest.builder().libelle("Contrôle").code("Cont").groupe_code("GRP001").position(1).structure_id(1).build();
            ActeRequest ar4 = ActeRequest.builder().libelle("Vaccinations").code("Vacc").groupe_code("GRP001").position(3).structure_id(1).build();
            ActeRequest ar5 = ActeRequest.builder().libelle("Injections").code("Vacc").groupe_code("GRP006").position(4).structure_id(1).build();
            ActeRequest ar6 = ActeRequest.builder().libelle("Pansements").code("Pans").groupe_code("GRP004").position(0).structure_id(1).build();
            ActeRequest ar7 = ActeRequest.builder().libelle("Kinésithérapie").code("kine").groupe_code("GRP005").position(0).structure_id(1).build();
            ActeRequest ar8 = ActeRequest.builder().libelle("Prise de sang").code("Ps").groupe_code("GRP001").position(0).structure_id(1).build();
            ActeRequest ar9 = ActeRequest.builder().libelle("Prélèvement Vaginal").code("PV").groupe_code("GRP001").position(0).structure_id(1).build();
            ActeRequest ar10 = ActeRequest.builder().libelle("IDR").code("idr").groupe_code("GRP001").position(0).structure_id(1).build();
            ActeRequest ar11 = ActeRequest.builder().libelle("Soins").code("Soin").groupe_code("GRP001").position(0).structure_id(1).build();
            ActeRequest ar12 = ActeRequest.builder().libelle("Radiographie").code("radio").groupe_code("GRP008").position(0).structure_id(1).build();
            ActeRequest ar13 = ActeRequest.builder().libelle("Echographie").code("echo").groupe_code("GRP009").position(0).structure_id(1).build();
            ActeRequest ar14 = ActeRequest.builder().libelle("ECG").code("ecg").groupe_code("GRP010").position(0).structure_id(1).build();
            ActeRequest ar15 = ActeRequest.builder().libelle("EEG").code("eeg").groupe_code("GRP011").position(0).structure_id(1).build();
            ActeRequest ar16 = ActeRequest.builder().libelle("Scanners").code("scan").groupe_code("GRP002").position(0).structure_id(1).build();
            ActeRequest ar17 = ActeRequest.builder().libelle("IRM").code("irm").groupe_code("GRP003").position(0).structure_id(1).build();
            ActeRequest ar18 = ActeRequest.builder().libelle("Laboratoire & Analyses").code("ana").groupe_code("GRP012").position(0).structure_id(1).build();
            ActeRequest ar19 = ActeRequest.builder().libelle("Endoscopie").code("endo").groupe_code("GRP013").position(0).structure_id(1).build();
            ActeRequest ar20 = ActeRequest.builder().libelle("Hémodialyse").code("hemo").groupe_code("GRP014").position(0).structure_id(1).build();
            ActeRequest ar21 = ActeRequest.builder().libelle("Médicaments").code("medic").groupe_code("GRP016").position(0).structure_id(1).build();
            ActeRequest ar22 = ActeRequest.builder().libelle("Consommables").code("conso").groupe_code("GRP017").position(0).structure_id(1).build();
            ActeRequest ar23 = ActeRequest.builder().libelle("Kits").code("kit").groupe_code("GRP015").position(0).structure_id(1).build();
            ActeRequest ar24 = ActeRequest.builder().libelle("Autres").code("Autres").groupe_code("GRP001").position(0).structure_id(1).build();

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

    private void seedPaysTable() {
        String sql = "SELECT c.nom FROM pays c";
        List<Pays> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
//            PaysRequest ar1 = PaysRequest.builder().code("TG").indicatif(228).nationalite("Togolaise").nom("TOGO").build();
//            PaysRequest ar2 = PaysRequest.builder().code("BN").indicatif(229).nationalite("Béninoise").nom("BENIN").build();
//            PaysRequest ar3 = PaysRequest.builder().code("GH").indicatif(233).nationalite("Ghanéenne").nom("GHANA").build();
//            paysService.addPays(Arrays.asList(ar1, ar2, ar3));
            List<PaysRequest> paysRequests = new ArrayList<PaysRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/secretariat/pays.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(";");
                PaysRequest sar = PaysRequest.builder()
                        .nom(cols[0].toUpperCase(Locale.FRANCE))
                        .code(cols[2])
                        .indicatif(cols[3])
                        .nationalite(cols[1])
                        .build();
                paysRequests.add(sar);
            });

            paysService.addPays(paysRequests);

            log.info("Pays table seeded");
        }else {
            log.info("Pays Seeding Not Required");
        }
    }

    private void seedVilleTable() {
        String sql = "SELECT c.nom FROM ville c";
        List<Ville> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            NameRequest ar1 = NameRequest.builder().nom("Lomé").build();
            NameRequest ar2 = NameRequest.builder().nom("Aného").build();
            NameRequest ar3 = NameRequest.builder().nom("Kpalimé").build();

            villeService.addVille(Arrays.asList(ar1, ar2, ar3));

            log.info("Ville table seeded");
        }else {
            log.info("Ville Seeding Not Required");
        }
    }

    private void seedCategorieTable() {
        String sql = "SELECT c.nom FROM categorie c";
        List<Categorie> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            NameRequest ar1 = NameRequest.builder().nom("PISJO").build();
            NameRequest ar2 = NameRequest.builder().nom("MEDECINS").build();
            NameRequest ar3 = NameRequest.builder().nom("CORPS MEDICAL").build();
            NameRequest ar4 = NameRequest.builder().nom("AMBASSADES").build();
            NameRequest ar5 = NameRequest.builder().nom("AGENCES DE VOYAGE").build();
            NameRequest ar6 = NameRequest.builder().nom("BANQUES").build();
            NameRequest ar7 = NameRequest.builder().nom("PHARMACIES").build();

            categorieService.addCategorie(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7));

            log.info("Categorie table seeded");
        }else {
            log.info("Categorie Seeding Not Required");
        }
    }

    private void seedQuartierTable() {
        String sql = "SELECT c.nom FROM quartier c";
        List<Quartier> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            NameRequest ar1 = NameRequest.builder().nom("Hédzranawoe").build();
            NameRequest ar2 = NameRequest.builder().nom("Gbossimé").build();
            NameRequest ar3 = NameRequest.builder().nom("Avé Maria").build();

            quartierService.addQuartier(Arrays.asList(ar1, ar2, ar3));

            log.info("Quartier table seeded");
        }else {
            log.info("Quartier Seeding Not Required");
        }
    }

    private void seedProfessionTable() {
        String sql = "SELECT c.denomination FROM profession c";
        List<Profession> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            ProfessionRequest ar1 = ProfessionRequest.builder().denomination("Ingénieur").build();
            ProfessionRequest ar2 = ProfessionRequest.builder().denomination("Agriculteur").build();
            ProfessionRequest ar3 = ProfessionRequest.builder().denomination("Zedman").build();

            professionService.addProfession(Arrays.asList(ar1, ar2, ar3));

            log.info("Profession table seeded");
        }else {
            log.info("Profession Seeding Not Required");
        }
    }

    private void seedEmployeurTable() {
        String sql = "SELECT c.nom FROM employeur c";
        List<Employeur> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            EmployeurRequest ar0 = EmployeurRequest.builder().nom("PISJO").email("pisjo@email.net").tel1("99999000").build();
            EmployeurRequest ar1 = EmployeurRequest.builder().nom("BIDC").email("bidc@email.net").tel1("99999999").build();
            EmployeurRequest ar2 = EmployeurRequest.builder().nom("TOGOCOM").email("tgcom@email.net").tel1("98989898").build();
            EmployeurRequest ar3 = EmployeurRequest.builder().nom("ARCEP").email("arcep@email.net").tel1("99989754").build();

            employeurService.addEmployeur(Arrays.asList(ar0, ar1, ar2, ar3));

            log.info("Employeur table seeded");
        }else {
            log.info("Employeur Seeding Not Required");
        }
    }

    private void seedTypeAssuranceTable() {
        String sql = "SELECT c.nom FROM type_assurance c";
        List<TypeAssurance> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            NameRequest ar1 = NameRequest.builder().nom("Locale").build();
            NameRequest ar2 = NameRequest.builder().nom("Etrangère").build();

            typeAssuranceService.addTypeAssurance(Arrays.asList(ar1, ar2));

            log.info("TypeAssurance table seeded");
        }else {
            log.info("TypeAssurance Seeding Not Required");
        }
    }

    private void seedModePayementTable() {
        String sql = "SELECT c.nom FROM mode_payement c";
        List<ModePayement> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            NameRequest ar1 = NameRequest.builder().nom("Espèces").build();
            NameRequest ar2 = NameRequest.builder().nom("Chèque").build();
            NameRequest ar3 = NameRequest.builder().nom("Visa").build();

            modePayementService.addModePayement(Arrays.asList(ar1, ar2, ar3));

            log.info("ModePayement table seeded");
        }else {
            log.info("ModePayement Seeding Not Required");
        }
    }

    private void seedEtatTable() {
        String sql = "SELECT c.nom FROM etat c";
        List<Etat> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            EtatRequest ar1 = EtatRequest.builder().nom("Attente chèque").couleur("blue").indice(1).build();
            EtatRequest ar2 = EtatRequest.builder().nom("Attente virement").couleur("blue").indice(1).build();
            EtatRequest ar3 = EtatRequest.builder().nom("Attente de payement").couleur("blue").indice(1).build();
            EtatRequest ar4 = EtatRequest.builder().nom("Erreur de paiement").couleur("red").indice(0).build();
            EtatRequest ar5 = EtatRequest.builder().nom("Annulée").couleur("red").indice(2).build();
            EtatRequest ar6 = EtatRequest.builder().nom("Payée").couleur("green").indice(3).build();
            EtatRequest ar7 = EtatRequest.builder().nom("Remboursée").couleur("red").indice(4).build();

            etatService.addEtat(Arrays.asList(ar1, ar2, ar3, ar4, ar5, ar6, ar7));

            log.info("Etat table seeded");
        }else {
            log.info("Etat Seeding Not Required");
        }
    }

    /*private void seedMedecinTable() {
        String sql = "SELECT c.nom FROM medecin c";
        List<Medecin> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            MedecinRequest ar1 = MedecinRequest.builder().nom("DOVI-AKUE").prenoms("Jean-Pierre").date_naissance(LocalDateTime.of(1955, Month.AUGUST, 01, 00, 00))
                    .sexe('M').lieu_naissance("Quelque part").tel1("90909090").email("adjp@email.net").type_piece("CNI").type("interne").no_piece("0000-000-0000").employeur_id(1).secteur_code("MIG").build();
            MedecinRequest ar2 = MedecinRequest.builder().nom("ABALO").prenoms("Serein").date_naissance(LocalDateTime.of(1955, Month.AUGUST, 01, 00, 00))
                    .sexe('M').lieu_naissance("Quelque part").tel1("90907878").email("aserein@email.net").type_piece("CNI").type("interne").no_piece("0000-000-0021").employeur_id(1).secteur_code("PDI").build();

            medecinService.addMedecin(Arrays.asList(ar1, ar2));

            log.info("Medecin table seeded");
        }else {
            log.info("Medecin Seeding Not Required");
        }
    }*/

    private void seedDeviseTable() {
        String sql = "SELECT c.nom FROM devise c";
        List<Devise> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            DeviseRequest ar1 = DeviseRequest.builder().nom("Franc CFA").code("CFA").symbol("XOF").taux(1).build();
            DeviseRequest ar2 = DeviseRequest.builder().nom("EURO").code("EUR").symbol("£").taux(700).build();
            DeviseRequest ar3 = DeviseRequest.builder().nom("Dollar").code("USD").symbol("$").taux(550).build();

            deviseService.addDevise(Arrays.asList(ar1, ar2, ar3 ));

            log.info("Devise table seeded");
        }else {
            log.info("Devise Seeding Not Required");

        }
    }

    private void seedAssuranceTable() {
        String sql = "SELECT c.nom FROM assurance c";
        List<Assurance> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(rs == null || rs.size() <= 0) {
            List<AssuranceRequest> assuranceRequests = new ArrayList<AssuranceRequest>();
            InputStream in = getClass().getResourceAsStream("/com/dopediatrie/hosman/secretariat/assurances.csv");

            assert in != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> {
                String[] cols = s.split(";");
                AssuranceRequest sar = AssuranceRequest.builder()
                        .nom(cols[0].toUpperCase(Locale.FRENCH))
                        .type_assurance(cols[1].toLowerCase(Locale.FRENCH))
                        .build();
                assuranceRequests.add(sar);
            });

            assuranceService.addAssurance(assuranceRequests);

            log.info("Assurance table seeded");
        }else {
            log.info("Assurance Seeding Not Required");
        }
    }
}
