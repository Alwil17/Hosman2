package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.payload.request.ProformatRequest;
import com.dopediatrie.hosman.secretariat.payload.request.TarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ProformatResponse;
import com.dopediatrie.hosman.secretariat.payload.response.TarifResponse;
import com.dopediatrie.hosman.secretariat.service.TarifService;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tarifs")
@RequiredArgsConstructor
@Log4j2
public class TarifController {

    private final TarifService tarifService;

    @Autowired
    SpringTemplateEngine templateEngine;


    @GetMapping
    public ResponseEntity<List<Tarif>> getAllTarifs() {

        log.info("TarifController | getAllTarifs is called");
        return new ResponseEntity<>(tarifService.getAllTarifs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addTarif(@RequestBody TarifRequest tarifRequest) {

        log.info("TarifController | addTarif is called");

        log.info("TarifController | addTarif | tarifRequest : " + tarifRequest.toString());

        long tarifId = tarifService.addTarif(tarifRequest);
        return new ResponseEntity<>(tarifId, HttpStatus.CREATED);
    }

    @PostMapping("/proformat")
    public ResponseEntity<Resource> generateProformat(@RequestBody ProformatRequest proformatRequest) throws IOException, DocumentException  {

        log.info("TarifController | generateProformat is called");

        String inputFilename = "proformat_portrait";
        String outputFilename = "proformat_portrait.pdf";

        List<ProformatResponse> responses = tarifService.processProformat(proformatRequest);

        Context context = new Context();
        context.setVariable("nom_patient", proformatRequest.getPatient());
        context.setVariable("date_heure", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
//        context.setVariable("patient",factureResponse.getReference());
        context.setVariable("recaps", responses);


        double totalUnit = responses.stream().mapToDouble(ProformatResponse::getPrix_unit).sum();
        double totalTotal = responses.stream().mapToDouble(ProformatResponse::getPrix_total).sum();

        context.setVariable("total_unit", totalUnit);
        context.setVariable("total_total", totalTotal);
        context.setVariable("montant_lettres", Utils.convertirMontantEnLettres(totalTotal)+" F CFA");

        String htmlContentToRender = templateEngine.process(inputFilename, context);
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

        OutputStream outputStream = new FileOutputStream("src//"+outputFilename);
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
        headers.setContentDispositionFormData(inputFilename, FileSystems
                .getDefault()
                .getPath("src")
                .resolve(outputFilename)
                .toUri().toURL().toString());

        Resource resource = new UrlResource(FileSystems
                .getDefault()
                .getPath("src")
                .resolve(outputFilename)
                .toUri());

        //return new ResponseEntity<>(factureResponse, HttpStatus.OK);
        return new ResponseEntity<>(resource, headers,  HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifResponse> getTarifById(@PathVariable("id") long tarifId) {

        log.info("TarifController | getTarifById is called");

        log.info("TarifController | getTarifById | tarifId : " + tarifId);

        TarifResponse tarifResponse
                = tarifService.getTarifById(tarifId);
        return new ResponseEntity<>(tarifResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Tarif>> getTarifBySearch(@RequestParam(value = "groupe", required = false) String groupeCode, @RequestParam(value = "acte", required = false) String acte) {
        log.info("TarifController | getTarifBySearch is called");
        List<Tarif> tarifResponses = Collections.emptyList();

        boolean checkGroupe = (groupeCode != null && !groupeCode.isBlank());
        boolean checkActe = (acte != null && !acte.isBlank());
        if(checkGroupe && checkActe){
            tarifResponses = tarifService.getTarifForGroupeAndActe(groupeCode, acte);
        }else if(checkGroupe == true && checkActe == false){
            tarifResponses = tarifService.getTarifForGroupe(groupeCode);
        }else if(checkGroupe == false && checkActe == true){
            tarifResponses = tarifService.getTarifForActe(acte);
        }

        return new ResponseEntity<>(tarifResponses, HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<TarifResponse> getTarifByCode(@PathVariable("code") String code) {
        log.info("TarifController | getTarifByCode is called");
        TarifResponse tarifResponse = tarifService.getTarifByCode(code);
        return new ResponseEntity<>(tarifResponse, HttpStatus.OK);
    }

    @GetMapping("/examens")
    public ResponseEntity<List<TarifResponse>> getTarifForExamen() {
        log.info("TarifController | getTarifForExamen is called");
        List<TarifResponse> tarifResponses = tarifService.getTarifForExamen();
        return new ResponseEntity<>(tarifResponses, HttpStatus.OK);
    }

    @Deprecated(forRemoval = true)
    @GetMapping("/groupe/{code}/all")
    public ResponseEntity<List<Tarif>> getTarifForGroupe(@PathVariable("code") String groupeCode) {
        log.info("TarifController | getTarifForGroupe is called");
        log.info("TarifController | getTarifForGroupe | groupeCode : " + groupeCode);

        List<Tarif> tarifResponses = tarifService.getTarifForGroupe(groupeCode);

        return new ResponseEntity<>(tarifResponses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editTarif(@RequestBody TarifRequest tarifRequest,
            @PathVariable("id") long tarifId
    ) {

        log.info("TarifController | editTarif is called");

        log.info("TarifController | editTarif | tarifId : " + tarifId);

        tarifService.editTarif(tarifRequest, tarifId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTarifById(@PathVariable("id") long tarifId) {
        tarifService.deleteTarifById(tarifId);
    }
}