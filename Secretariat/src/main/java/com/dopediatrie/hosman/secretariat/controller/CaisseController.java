package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.*;
import com.dopediatrie.hosman.secretariat.payload.response.CaisseResponse;
import com.dopediatrie.hosman.secretariat.payload.response.ErrorResponse;
import com.dopediatrie.hosman.secretariat.service.CaisseService;
import com.dopediatrie.hosman.secretariat.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.internal.lang.reflect.DeclareParentsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.URI;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/caisse")
@RequiredArgsConstructor
@Log4j2
public class CaisseController {

    private final CaisseService caisseService;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SpringTemplateEngine templateEngine;

    @GetMapping
    public ResponseEntity<CaisseResponse> getCurrentCaisse() {
        log.info("CaisseController | getCurrentCaisse is called");
        CaisseResponse caisseResponse = caisseService.getCurrentCaisse();
        return new ResponseEntity<>(caisseResponse, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<Resource> getDailyReport(@RequestParam(value = "datemin", required = false) String datemin, @RequestParam(value = "datemax", required = false) String datemax, @RequestParam(value = "vue") String vue) throws IOException {
        log.info("CaisseController | getDailyReport is called");
        HttpHeaders headers = new HttpHeaders();
        Resource resource;
        URI uri;
        if(datemin == null && datemax == null){
            uri = getReportForCurrentDate(vue);
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", uri.toURL().toString());

            resource = new UrlResource(uri);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }

        LocalDateTime dateOuverture = LocalDateTime.now();
        LocalDateTime dateFermeture = LocalDateTime.now();
        List<Caisse> caisses = new ArrayList<Caisse>();

        if(datemin != null && !datemin.isBlank()){
            String dateOuv = datemin +"T00:00:00";
            String dateFer;
            dateOuverture = LocalDateTime.parse(dateOuv);
            // si datemin seul a été soumis
            if(datemax == null){
                String libelleCaisse = "Caisse_"+datemin;
                Caisse caisse = caisseService.getCaisseByLibelle(libelleCaisse);
                if (caisse == null){
                    dateFer = datemin +"T23:59:59";
                    dateFermeture = LocalDateTime.parse(dateFer);

                    CaisseResponse currentCaisse = caisseService.getCurrentCaisse();
                    if(currentCaisse != null && currentCaisse.getDate_ouverture().isBefore(dateOuverture)){
                        dateOuverture = currentCaisse.getDate_ouverture();
                        caisse = new Caisse();
                        copyProperties(currentCaisse, caisse);
                        caisses.add(caisse);
                    }else{
                        caisses = caisseService.getCaisseByDateminAndDatexax(dateOuverture, dateFermeture);
                    }
                    //dateOuverture = LocalDateTime.parse(dateOuv);

                }else {
                    dateOuverture = caisse.getDate_ouverture();
                    if(caisse.getDate_fermeture() == null){
                        dateFer = datemin +"T23:59:59";
                        dateFermeture = LocalDateTime.parse(dateFer);
                        caisse.setDate_fermeture(dateFermeture);
                    }else{
                        dateFermeture = caisse.getDate_fermeture();
                    }
                    caisses.add(caisse);

                }
            }else{
                dateFer = datemax +"T23:59:59";
                dateFermeture = LocalDateTime.parse(dateFer);
                caisses = caisseService.getCaisseByDateminAndDatexax(dateOuverture, dateFermeture);
            }
        }

        // Avoir la plus petite date_ouverture et la plus grande date_fermeture
        if (caisses == null || (caisses != null && caisses.size() <= 0)){
            headers.setContentType(MediaType.TEXT_PLAIN);
            resource = new ByteArrayResource("Aucune caisse trouvee pour cette periode".getBytes());
        }else{
            LocalDateTime minDate = dateOuverture;
            LocalDateTime maxDate = dateFermeture;

            for (Caisse caisse : caisses) {
                if (caisse.getDate_ouverture().isBefore(minDate)) {
                    dateOuverture = caisse.getDate_ouverture();
                }
                if (caisse.getDate_fermeture() != null && caisse.getDate_fermeture().isAfter(maxDate)) {
                    dateFermeture = caisse.getDate_fermeture();
                }
            }
            // Les min et max ont été déterminés.
            log.info(dateOuverture);
            log.info(dateFermeture);
            uri = generateReportFor(dateOuverture, dateFermeture, vue);
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", uri.toURL().toString());

            resource = new UrlResource(uri);
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    public URI generateReportFor(LocalDateTime dateOuverture, LocalDateTime dateFermerture, String vue) throws IOException {
        String datemin = dateOuverture.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String datemax = dateFermerture.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String datedebut = dateOuverture.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String datefin = dateFermerture.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        /*String sql = "select a.libelle as acte, b.* \n" +
                "from acte a \n" +
                "left join (SELECT a.libelle AS libelle, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_especes, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN pt.total_price_gros ELSE 0 END\n" +
                "  ) AS total_especes, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_cheque, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN pt.total_price_gros ELSE 0 END\n" +
                "  ) AS total_cheque, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_visa, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN pt.total_price_gros ELSE 0 END\n" +
                "  ) AS total_visa,\n" +
                "  SUM(pt.total_price_gros) AS montant_total \n" +
                "FROM \n" +
                "  facture f \n" +
                "  JOIN prestation p ON f.prestation_id = p.id \n" +
                "  JOIN prestation_tarif pt ON p.id = pt.prestation_id \n" +
                "  JOIN tarif t ON pt.tarif_id = t.id \n" +
                "  JOIN acte a ON t.acte_id = a.id \n" +
                "  JOIN mode_facture fm ON f.id = fm.facture_id \n" +
                "  JOIN mode_payement m ON fm.mode_payement_id = m.id \n" +
                "WHERE \n" +
                "  f.date_facture >= '"+ datemin +"' \n" +
                "  and f.date_facture <= '"+ datemax +"' \n" +
                "GROUP BY a.libelle)b on a.libelle = b.libelle order by a.position asc";*/
        String sql = Utils.getQuery(datemin, datemax, vue);
        List<FicheRecap> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> new FicheRecap(
                resultSet.getString("actec"),
                resultSet.getInt("nb_especes"),
                resultSet.getDouble("total_especes"),
                resultSet.getInt("nb_cheque"),
                resultSet.getDouble("total_cheque"),
                resultSet.getInt("nb_visa"),
                resultSet.getDouble("total_visa"),
                resultSet.getInt("nb_pec"),
                resultSet.getDouble("total_pec"),
                resultSet.getDouble("montant_total")
        ));
        String creancesql = "select * from creance join etat e on creance.etat_id = e.id " +
                "where e.slug = 'payee' " +
                "and creance.date_reglement >= '"+ datemin +"' " +
                "and creance.date_reglement <= '"+ datemax +"'  and montant >0";
        /*List<Creance> creances = jdbcTemplate.query(sql, (resultSet, rowNum) -> new Creance(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getDate("date_operation"),
                resultSet.getDate("date_reglement"),
                resultSet.getLong("etat_id"),
                resultSet.getLong("patient_id")
        ));*/
        FicheRecap creance = new FicheRecap("Règlement de Créance",
                0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Creance> creances = jdbcTemplate.query(creancesql, (resultSet, rowNum) -> null);
        if(creances != null && creances.size() > 0){
            for (Creance c : creances) {
                creance.setNb_especes(creance.getNb_especes()+1);
                creance.setTotal_especes(creance.getTotal_especes() + c.getMontant());
                creance.setMontant_total(creance.getTotal_especes() + c.getMontant());
            }
        }
        rs.add(creance);

        String majorationsql = "select * from majoration " +
                "where date_operation >= '"+ datemin +"' " +
                "and date_operation <= '"+ datemax +"'  and montant >0";
        FicheRecap majoration = new FicheRecap("Majoration",
                0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Majoration> majorations = jdbcTemplate.query(majorationsql, (resultSet, rowNum) -> new Majoration(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getString("motif"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
        ));
        if(majorations != null && majorations.size() > 0){
            for (Majoration c : majorations) {
                if(c != null) {
                    majoration.setNb_especes(majoration.getNb_especes()+1);
                    majoration.setTotal_especes(majoration.getTotal_especes() + c.getMontant());
                    majoration.setMontant_total(majoration.getTotal_especes() + c.getMontant());
                }
            }
        }
        rs.add(majoration);

        String encaissementsql = "select 'HP Comptabilité' as acte,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN 1 ELSE 0 END\n" +
                ") AS nb_especes,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN em.montant ELSE 0 END\n" +
                ") AS total_especes,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN 1 ELSE 0 END\n" +
                ") AS nb_cheque,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN em.montant ELSE 0 END\n" +
                ") AS total_cheque,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN 1 ELSE 0 END\n" +
                ") AS nb_visa,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN em.montant ELSE 0 END\n" +
                ") AS total_visa, \n" +
                "SUM(em.montant) AS montant_total \n" +
                "from encaissement e\n" +
                "join mode_encaissement em on em.encaissement_id = e.id \n" +
                "join mode_payement m on em.mode_payement_id = m.id \n" +
                "where e.date_encaissement >= '"+ datemin +"' \n" +
                "and e.date_encaissement <= '"+ datemax +"' \n" +
                "and e.provenance = 'compta'";

        List<FicheRecap> encaissements = jdbcTemplate.query(encaissementsql, (resultSet, rowNum) -> new FicheRecap(
                resultSet.getString("acte"),
                resultSet.getInt("nb_especes"),
                resultSet.getDouble("total_especes"),
                resultSet.getInt("nb_cheque"),
                resultSet.getDouble("total_cheque"),
                resultSet.getInt("nb_visa"),
                resultSet.getDouble("total_visa"),
                0,
                0,
                resultSet.getDouble("montant_total")
        ));
        if(encaissements != null && encaissements.size() > 0){
            rs.addAll(encaissements);
        }
        FicheRecap hpsecretariat = new FicheRecap("HP Secretariat",
                0, 0, 0, 0, 0, 0, 0, 0, 0);
        rs.add(hpsecretariat);

        int total_nb_especes = 0;
        int total_total_especes = 0;
        int total_nb_cheque = 0;
        int total_total_cheque = 0;
        int total_nb_visa = 0;
        int total_total_visa = 0;
        int total_nb_pec = 0;
        int total_total_pec = 0;
        int total_montant_total = 0;
        for (FicheRecap ficheRecap : rs) {
            total_nb_especes += ficheRecap.getNb_especes();
            total_total_especes += ficheRecap.getTotal_especes();
            total_nb_cheque += ficheRecap.getNb_cheque();
            total_total_cheque += ficheRecap.getTotal_cheque();
            total_nb_visa += ficheRecap.getNb_visa();
            total_total_visa += ficheRecap.getTotal_visa();
            total_nb_pec += ficheRecap.getNb_pec();
            total_total_pec += ficheRecap.getTotal_pec();
            total_montant_total += ficheRecap.getMontant_total();
        }

        double remises = 0;
        String reductionsql = "select * from reduction " +
                "where date_operation >= '"+ datemin +"' " +
                "and date_operation <= '"+ datemax +"'";
        List<Reduction> reductions = jdbcTemplate.query(reductionsql, (resultSet, rowNum) -> new Reduction(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getString("motif"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
        ));
        if(reductions != null && reductions.size() > 0){
            for (Reduction c : reductions) {
                if(c != null) {
                    remises += c.getMontant();
                }
            }
        }

        double dettesc = 0;
        String dettesql = "select * from creance join etat e on creance.etat_id = e.id " +
                "where e.slug != 'payee' " +
                "and creance.date_operation >= '"+ datemin +"' " +
                "and creance.date_operation <= '"+ datemax +"'";
        List<Creance> dettes = jdbcTemplate.query(dettesql, (resultSet, rowNum) -> new Creance(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
        ));
        if(dettes != null && dettes.size() > 0){
            for (Creance c : dettes) {
                if(c != null){
                    dettesc += c.getMontant();
                }
            }
        }

        double depensesc = 0;
        String depensesql = "select * from depense " +
                "where date_depense >= '"+ datemin +"' " +
                "and date_depense <= '"+ datemax +"'";
        List<Depense> depenses = jdbcTemplate.query(depensesql, (resultSet, rowNum) -> new Depense(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getString("motif"),
                resultSet.getTimestamp("date_depense").toLocalDateTime(),
                resultSet.getTimestamp("date_modification").toLocalDateTime()
        ));
        if(depenses != null && depenses.size() > 0){
            for (Depense c : depenses) {
                if(c != null){
                    depensesc += c.getMontant();
                }
            }
        }

        double reliquatsc = 0;
        String reliquatsql = "select * from reliquat join etat e on reliquat.etat_id = e.id " +
                "where e.slug = 'payee' " +
                "and date_operation >= '"+ datemin +"' " +
                "and date_operation <= '"+ datemax +"'";

        List<Reliquat> reliquats = jdbcTemplate.query(reliquatsql, (resultSet, rowNum) -> new Reliquat(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
        ));
        if(reliquats != null && reliquats.size() > 0){
            System.out.println(reliquats.size());
            for (Reliquat c : reliquats) {
                if(c != null){
                    System.out.println(datemin + " "+ datemax + " "+c.getMontant());
                    reliquatsc += c.getMontant();
                }
            }
        }

        double ca_total = total_montant_total - depensesc - reliquatsc;

        Context context = new Context();
        context.setVariable("date_debut", datedebut);
        context.setVariable("date_fin", datefin);
        context.setVariable("recaps", rs);
        context.setVariable("total_nb_especes", total_nb_especes);
        context.setVariable("total_total_especes", total_total_especes);
        context.setVariable("total_nb_cheque", total_nb_cheque);
        context.setVariable("total_total_cheque", total_total_cheque);
        context.setVariable("total_nb_visa", total_nb_visa);
        context.setVariable("total_total_visa", total_total_visa);
        context.setVariable("total_nb_pec", total_nb_pec);
        context.setVariable("total_total_pec", total_total_pec);
        context.setVariable("total_montant_total", total_montant_total);
        context.setVariable("remises", remises);
        context.setVariable("creances", dettesc);
        context.setVariable("depenses", depensesc);
        context.setVariable("reliquats", reliquatsc);
        context.setVariable("ca_total", ca_total);

        String fiche = vue.equals("compact") ? "ficherecapperiod" : "ficherecapperiod_portrait";
        String htmlContentToRender = templateEngine.process(fiche, context);
        String xHtml = Utils.xhtmlConvert(htmlContentToRender);

        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);

        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources", "templates")
                .toUri()
                .toURL()
                .toString();

        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        OutputStream outputStream = new FileOutputStream("src//recapperiod.pdf");
        renderer.createPDF(outputStream);
        outputStream.close();

        return FileSystems
                .getDefault()
                .getPath("src")
                .resolve("recapperiod.pdf")
                .toUri();
    }

    public URI getReportForCurrentDate(String vue) throws IOException {

        CaisseResponse caisse = caisseService.getCurrentCaisse();
        String datemin = caisse.getDate_ouverture().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String datemax;
        String datejour;
        if(caisse.getDate_fermeture() == null){
            datemax = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            datejour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }else{
            datemax = caisse.getDate_fermeture().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            datejour = caisse.getDate_fermeture().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        /*String sql = "select a.libelle as acte, b.* \n" +
                "from acte a \n" +
                "left join (SELECT a.libelle AS libelle, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_especes, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN pt.total_price_gros ELSE 0 END\n" +
                "  ) AS total_especes, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_cheque, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN pt.total_price_gros ELSE 0 END\n" +
                "  ) AS total_cheque, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_visa, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN pt.total_price_gros ELSE 0 END\n" +
                "  ) AS total_visa,\n" +
                "  SUM(pt.total_price_gros) AS montant_total \n" +
                "FROM \n" +
                "  facture f \n" +
                "  JOIN prestation p ON f.prestation_id = p.id \n" +
                "  JOIN prestation_tarif pt ON p.id = pt.prestation_id \n" +
                "  JOIN tarif t ON pt.tarif_id = t.id \n" +
                "  JOIN acte a ON t.acte_id = a.id \n" +
                "  JOIN mode_facture fm ON f.id = fm.facture_id \n" +
                "  JOIN mode_payement m ON fm.mode_payement_id = m.id \n" +
                "WHERE \n" +
                "  f.date_facture >= '"+ datemin +"' \n" +
                "  and f.date_facture <= '"+ datemax +"' \n" +
                "GROUP BY a.libelle)b on a.libelle = b.libelle order by a.position asc";*/
        String sql = Utils.getQuery(datemin, datemax, vue);
        List<FicheRecap> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> new FicheRecap(
                resultSet.getString("actec"),
                resultSet.getInt("nb_especes"),
                resultSet.getDouble("total_especes"),
                resultSet.getInt("nb_cheque"),
                resultSet.getDouble("total_cheque"),
                resultSet.getInt("nb_visa"),
                resultSet.getDouble("total_visa"),
                resultSet.getInt("nb_pec"),
                resultSet.getDouble("total_pec"),
                resultSet.getDouble("montant_total")
        ));
        String creancesql = "select * from creance join etat e on creance.etat_id = e.id " +
                "where e.slug = 'payee' " +
                "and creance.date_reglement >= '"+ datemin +"' " +
                "and creance.date_reglement <= '"+ datemax +"'  and montant >0";
        /*List<Creance> creances = jdbcTemplate.query(sql, (resultSet, rowNum) -> new Creance(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getDate("date_operation"),
                resultSet.getDate("date_reglement"),
                resultSet.getLong("etat_id"),
                resultSet.getLong("patient_id")
        ));*/
        FicheRecap creance = new FicheRecap("Règlement de Créance",
                0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Creance> creances = jdbcTemplate.query(creancesql, (resultSet, rowNum) -> null);
        if(creances != null && creances.size() > 0){
            for (Creance c : creances) {
                creance.setNb_especes(creance.getNb_especes()+1);
                creance.setTotal_especes(creance.getTotal_especes() + c.getMontant());
                creance.setMontant_total(creance.getTotal_especes() + c.getMontant());
            }
        }
        rs.add(creance);

        String majorationsql = "select * from majoration " +
                "where date_operation >= '"+ datemin +"' " +
                "and date_operation <= '"+ datemax +"'  and montant >0";
        FicheRecap majoration = new FicheRecap("Majoration",
                0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Majoration> majorations = jdbcTemplate.query(majorationsql, (resultSet, rowNum) -> new Majoration(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getString("motif"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
        ));
        if(majorations != null && majorations.size() > 0){
            for (Majoration c : majorations) {
                if(c != null) {
                    majoration.setNb_especes(majoration.getNb_especes()+1);
                    majoration.setTotal_especes(majoration.getTotal_especes() + c.getMontant());
                    majoration.setMontant_total(majoration.getTotal_especes() + c.getMontant());
                }
            }
        }
        rs.add(majoration);

        String encaissementsql = "select 'HP Comptabilité' as acte,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN 1 ELSE 0 END\n" +
                ") AS nb_especes,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN em.montant ELSE 0 END\n" +
                ") AS total_especes,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN 1 ELSE 0 END\n" +
                ") AS nb_cheque,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN em.montant ELSE 0 END\n" +
                ") AS total_cheque,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN 1 ELSE 0 END\n" +
                ") AS nb_visa,\n" +
                "SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN em.montant ELSE 0 END\n" +
                ") AS total_visa, \n" +
                "SUM(em.montant) AS montant_total \n" +
                "from encaissement e\n" +
                "join mode_encaissement em on em.encaissement_id = e.id \n" +
                "join mode_payement m on em.mode_payement_id = m.id \n" +
                "where e.date_encaissement >= '"+ datemin +"' \n" +
                "and e.date_encaissement <= '"+ datemax +"' \n" +
                "and e.provenance = 'compta'";

        List<FicheRecap> encaissements = jdbcTemplate.query(encaissementsql, (resultSet, rowNum) -> new FicheRecap(
                resultSet.getString("acte"),
                resultSet.getInt("nb_especes"),
                resultSet.getDouble("total_especes"),
                resultSet.getInt("nb_cheque"),
                resultSet.getDouble("total_cheque"),
                resultSet.getInt("nb_visa"),
                resultSet.getDouble("total_visa"),
                0,
                0,
                resultSet.getDouble("montant_total")
        ));
        if(encaissements != null && encaissements.size() > 0){
            rs.addAll(encaissements);
        }
        FicheRecap hpsecretariat = new FicheRecap("HP Secretariat",
                0, 0, 0, 0, 0, 0, 0, 0, 0);
        rs.add(hpsecretariat);

        int total_nb_especes = 0;
        int total_total_especes = 0;
        int total_nb_cheque = 0;
        int total_total_cheque = 0;
        int total_nb_visa = 0;
        int total_total_visa = 0;
        int total_nb_pec = 0;
        int total_total_pec = 0;
        int total_montant_total = 0;
        for (FicheRecap ficheRecap : rs) {
            total_nb_especes += ficheRecap.getNb_especes();
            total_total_especes += ficheRecap.getTotal_especes();
            total_nb_cheque += ficheRecap.getNb_cheque();
            total_total_cheque += ficheRecap.getTotal_cheque();
            total_nb_visa += ficheRecap.getNb_visa();
            total_total_visa += ficheRecap.getTotal_visa();
            total_nb_pec += ficheRecap.getNb_pec();
            total_total_pec += ficheRecap.getTotal_pec();
            total_montant_total += ficheRecap.getMontant_total();
        }

        double remises = 0;
        String reductionsql = "select * from reduction " +
                "where date_operation >= '"+ datemin +"' " +
                "and date_operation <= '"+ datemax +"'";
        List<Reduction> reductions = jdbcTemplate.query(reductionsql, (resultSet, rowNum) -> new Reduction(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getString("motif"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
        ));
        if(reductions != null && reductions.size() > 0){
            for (Reduction c : reductions) {
                if(c != null) {
                    remises += c.getMontant();
                }
            }
        }

        double dettesc = 0;
        String dettesql = "select * from creance join etat e on creance.etat_id = e.id " +
                "where e.slug != 'payee' " +
                "and creance.date_operation >= '"+ datemin +"' " +
                "and creance.date_operation <= '"+ datemax +"'";
        List<Creance> dettes = jdbcTemplate.query(dettesql, (resultSet, rowNum) -> new Creance(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
                ));
        if(dettes != null && dettes.size() > 0){
            for (Creance c : dettes) {
                if(c != null){
                    dettesc += c.getMontant();
                }
            }
        }

        double depensesc = 0;
        String depensesql = "select * from depense " +
                "where date_depense >= '"+ datemin +"' " +
                "and date_depense <= '"+ datemax +"'";
        List<Depense> depenses = jdbcTemplate.query(depensesql, (resultSet, rowNum) -> new Depense(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getString("motif"),
                resultSet.getTimestamp("date_depense").toLocalDateTime(),
                resultSet.getTimestamp("date_modification").toLocalDateTime()
        ));
        if(depenses != null && depenses.size() > 0){
            for (Depense c : depenses) {
                if(c != null){
                    depensesc += c.getMontant();
                }
            }
        }

        double reliquatsc = 0;
        String reliquatsql = "select * from reliquat join etat e on reliquat.etat_id = e.id " +
                "where e.slug = 'payee' " +
                "and date_operation >= '"+ datemin +"' " +
                "and date_operation <= '"+ datemax +"'";

        List<Reliquat> reliquats = jdbcTemplate.query(reliquatsql, (resultSet, rowNum) -> new Reliquat(
                resultSet.getLong("id"),
                resultSet.getDouble("montant"),
                resultSet.getTimestamp("date_operation").toLocalDateTime()
        ));
        if(reliquats != null && reliquats.size() > 0){
            System.out.println(reliquats.size());
            for (Reliquat c : reliquats) {
                if(c != null){
                    //System.out.println(datemin + " "+ datemax + " "+c.getMontant());
                    reliquatsc += c.getMontant();
                }
            }
        }

        //System.out.println(datemin + " "+ datemax + " "+reliquatsc);
        double ca_total = total_montant_total - depensesc - reliquatsc;

        Context context = new Context();
        context.setVariable("date_jour", datejour);
        context.setVariable("recaps", rs);
        context.setVariable("total_nb_especes", total_nb_especes);
        context.setVariable("total_total_especes", total_total_especes);
        context.setVariable("total_nb_cheque", total_nb_cheque);
        context.setVariable("total_total_cheque", total_total_cheque);
        context.setVariable("total_nb_visa", total_nb_visa);
        context.setVariable("total_total_visa", total_total_visa);
        context.setVariable("total_nb_pec", total_nb_pec);
        context.setVariable("total_total_pec", total_total_pec);
        context.setVariable("total_montant_total", total_montant_total);
        context.setVariable("remises", remises);
        context.setVariable("creances", dettesc);
        context.setVariable("depenses", depensesc);
        context.setVariable("reliquats", reliquatsc);
        context.setVariable("ca_total", ca_total);

        String htmlContentToRender = templateEngine.process("ficherecap", context);
        String xHtml = Utils.xhtmlConvert(htmlContentToRender);

        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);

        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources", "templates")
                .toUri()
                .toURL()
                .toString();

        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        OutputStream outputStream = new FileOutputStream("src//recap.pdf");
        renderer.createPDF(outputStream);
        outputStream.close();

//        return new ResponseEntity<>(rs, HttpStatus.OK);
        return FileSystems
                .getDefault()
                .getPath("src")
                .resolve("recap.pdf")
                .toUri();
    }
}