package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Caisse;
import com.dopediatrie.hosman.secretariat.entity.FicheRecap;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CaisseResponse;
import com.dopediatrie.hosman.secretariat.repository.CaisseRepository;
import com.dopediatrie.hosman.secretariat.service.CaisseService;
import com.dopediatrie.hosman.secretariat.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/caisse")
@RequiredArgsConstructor
@Log4j2
public class CaisseController {

    private final CaisseRepository caisseRepository;
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
    public ResponseEntity<Resource> getDailyReport() throws IOException {
        log.info("CaisseController | getDailyReport is called");
        CaisseResponse caisse = caisseService.getCurrentCaisse();
        String datemin = caisse.getDate_ouverture().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String datemax = "";
        if(caisse.getDate_fermeture() == null){
            datemax = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }else{
            datemax = caisse.getDate_fermeture().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        String sql = "select a.libelle as acte, b.* \n" +
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
                "  and f.date_facture < '"+ datemax +"' \n" +
                "GROUP BY a.libelle)b on a.libelle = b.libelle order by a.position asc";
        List<FicheRecap> rs = jdbcTemplate.query(sql, (resultSet, rowNum) -> new FicheRecap(
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

        Context context = new Context();
        context.setVariable("date_heure", caisse.getDate_ouverture().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", FileSystems
                .getDefault()
                .getPath("src")
                .resolve("recap.pdf")
                .toUri().toURL().toString());

        Resource resource = new UrlResource(FileSystems
                .getDefault()
                .getPath("src")
                .resolve("recap.pdf")
                .toUri());

//        return new ResponseEntity<>(rs, HttpStatus.OK);
        return new ResponseEntity<>(resource, headers,  HttpStatus.OK);
    }
}