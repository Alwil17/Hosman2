package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.*;
import com.dopediatrie.hosman.secretariat.payload.request.FactureRequest;
import com.dopediatrie.hosman.secretariat.payload.response.FactureResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PatientResponse;
import com.dopediatrie.hosman.secretariat.repository.FactureModeRepository;
import com.dopediatrie.hosman.secretariat.repository.PrestationTarifRepository;
import com.dopediatrie.hosman.secretariat.service.FactureService;
import com.dopediatrie.hosman.secretariat.utils.Utils;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/factures")
@RequiredArgsConstructor
@Log4j2
public class FactureController {

    private final FactureService factureService;
    private final PrestationTarifRepository tarifRepository;
    private final FactureModeRepository factureModeRepository;

    @Autowired
    SpringTemplateEngine templateEngine;

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {

        log.info("FactureController | getAllFactures is called");
        return new ResponseEntity<>(factureService.getAllFactures(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addFacture(@RequestBody FactureRequest factureRequest) {

        log.info("FactureController | addFacture is called");

        log.info("FactureController | addFacture | factureRequest : " + factureRequest.toString());

        long factureId = factureService.addFacture(factureRequest);
        return new ResponseEntity<>(factureId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactureResponse> getFactureById(@PathVariable("id") long factureId) {

        log.info("FactureController | getFactureById is called");

        log.info("FactureController | getFactureById | factureId : " + factureId);

        FactureResponse factureResponse
                = factureService.getFactureById(factureId);
        return new ResponseEntity<>(factureResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Facture>> getFactureBySearch(@RequestParam(value = "datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax, @RequestParam(value = "code", required = false) String code) {
        log.info("FactureController | getFactureBySearch is called");
        List<Facture> factures = Collections.emptyList();
        String dD = datemin+"T00:00:00";
        String dF;
        if(datemax == null){
            dF = datemin + "T23:59:59";
        }else{
            dF = datemax + "T23:59:59";
        }
        LocalDateTime dateDebut = LocalDateTime.parse(dD);
        LocalDateTime dateFin = LocalDateTime.parse(dF);
        if(code != null && !code.isBlank()){
            factures = factureService.getFactureByDateMinAndMaxAndCode(dateDebut, dateFin, code);
        }else{
            factures = factureService.getFactureByDateMinAndMax(dateDebut, dateFin);
        }
        return new ResponseEntity<>(factures, HttpStatus.OK);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<Resource> displayFactureById(@PathVariable("id") long factureId) throws IOException, DocumentException {

        log.info("FactureController | displayFactureById is called");

        log.info("FactureController | displayFactureById | factureId : " + factureId);

        FactureResponse factureResponse
                = factureService.getFactureById(factureId);

        Prestation prestation = factureResponse.getPrestation();
        Patient patient = prestation.getPatient();

        List<PrestationTarif> tarifs = tarifRepository.findByPrestationId(prestation.getId());
        String groupe = "";
        if(prestation.getSecteur() == null){
            groupe = tarifs.get(0).getTarif().getActe().getGroupe().getLibelle();
        }else{
            groupe = prestation.getSecteur().getLibelle();
        }
        long nuum = factureResponse.getAttente() != null ? factureResponse.getAttente().getNum_attente() : 1;
        double verse = 0;
        List<FactureMode> fms = factureModeRepository.findByFacture_Id(factureId);
        for (FactureMode fm: fms
             ) {
            if(fm.getMode_payement().getSlug().equals("especes")){
                verse += fm.getMontant();
            }
        }
        String assurance_nom = "";
        double taux_assurance = 0;
        if(patient.getAssurance() != null){
            assurance_nom = patient.getAssurance().getNom();
            taux_assurance = patient.getTaux_assurance();
        }else{
            assurance_nom = "LUI-MÊME";
            taux_assurance = 0;
        }
        Context context = new Context();
        context.setVariable("reference",factureResponse.getReference());
        context.setVariable("date_heure",factureResponse.getDate_facture().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        context.setVariable("patient",factureResponse.getReference());
        context.setVariable("total",factureResponse.getTotal());
        context.setVariable("montant_pec",factureResponse.getMontant_pec());
        context.setVariable("majoration",factureResponse.getMajoration().getMontant());
        context.setVariable("reduction",factureResponse.getReduction().getMontant());
        context.setVariable("a_payer",factureResponse.getA_payer());
        context.setVariable("verse", verse);
        context.setVariable("reliquat",factureResponse.getReliquat().getMontant());
        context.setVariable("patient", patient);
        context.setVariable("date_naissance", patient.getDate_naissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("tarifs", tarifs);
        context.setVariable("groupe", groupe);
        context.setVariable("attente", nuum);
        context.setVariable("nom_assurance", assurance_nom);
        context.setVariable("taux_assurance", taux_assurance);
        context.setVariable("mode", factureResponse.getMode_payements().get(0));


        String htmlContentToRender = templateEngine.process("invoice", context);
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

        OutputStream outputStream = new FileOutputStream("src//test.pdf");
        renderer.createPDF(outputStream);
        outputStream.close();
        /*
        factureResponse.setPath(FileSystems
                .getDefault()
                .getPath("src")
                .resolve("test.pdf")
                .toUri().toURL().toString());*/
        //System.out.println(outputStream.);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", FileSystems
                .getDefault()
                .getPath("src")
                .resolve("test.pdf")
                .toUri().toURL().toString());

        Resource resource = new UrlResource(FileSystems
                .getDefault()
                .getPath("src")
                .resolve("test.pdf")
                .toUri());

        //return new ResponseEntity<>(factureResponse, HttpStatus.OK);
        return new ResponseEntity<>(resource, headers,  HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editFacture(@RequestBody FactureRequest factureRequest,
            @PathVariable("id") long factureId
    ) {

        log.info("FactureController | editFacture is called");

        log.info("FactureController | editFacture | factureId : " + factureId);

        factureService.editFacture(factureRequest, factureId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFactureById(@PathVariable("id") long factureId) {
        factureService.deleteFactureById(factureId);
    }
}