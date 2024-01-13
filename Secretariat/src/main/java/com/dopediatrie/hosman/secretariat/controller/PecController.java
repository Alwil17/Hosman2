package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.payload.response.PECCreanceResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECDetailsResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECResponse;
import com.dopediatrie.hosman.secretariat.service.PECService;
import com.dopediatrie.hosman.secretariat.utils.Utils;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/factures/pecs")
@RequiredArgsConstructor
@Log4j2
public class PecController {

    private final PECService pecService;

    @Autowired
    SpringTemplateEngine templateEngine;

    @GetMapping("/search")
    public ResponseEntity<List<PECResponse>> getPECBySearch(@RequestParam(value = "datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax,
                                                    @Schema(type = "string", description = "Type de l'assurance", allowableValues = {"locale", "etrangere"}) @RequestParam(value = "type", required = false) String assur_type,
                                                    @Schema(type = "string", description = "Slug unique de l'assurance") @RequestParam(value = "slug", required = false) String assur_slug) {
        log.info("PecController | getPECBySearch is called");
        List<PECResponse> pecs = Collections.emptyList();
        String dD = datemin+"T00:00:00";
        String dF;
        if(datemax == null){
            dF = datemin + "T23:59:59";
        }else{
            dF = datemax + "T23:59:59";
        }
        LocalDateTime dateDebut = LocalDateTime.parse(dD);
        LocalDateTime dateFin = LocalDateTime.parse(dF);
        boolean checkType = (assur_type != null && !assur_type.isBlank());
        boolean checkSlug = (assur_slug != null && !assur_slug.isBlank());
        if(checkType && checkSlug){
            pecs = pecService.getPECByDateMinAndMaxAndTypeAndSlug(dateDebut, dateFin, assur_type, assur_slug);
        }else if(checkType == true && checkSlug == false){
            pecs = pecService.getPECByDateMinAndMaxAndType(dateDebut, dateFin, assur_type);
        }else if(checkType == false && checkSlug == true){
            pecs = pecService.getPECByDateMinAndMaxAndAssur(dateDebut, dateFin, assur_slug);
        }else {
            pecs = pecService.getPECByDateMinAndMax(dateDebut, dateFin);
        }
        return new ResponseEntity<>(pecs, HttpStatus.OK);
    }

    @GetMapping("/print")
    public ResponseEntity<Resource> displayPECBySearch(@RequestParam(value = "datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax,
                                                       @Schema(type = "string", description = "Type de l'assurance", allowableValues = {"locale", "etrangere"}) @RequestParam(value = "type", required = false) String assur_type,
                                                       @Schema(type = "string", description = "Slug unique de l'assurance") @RequestParam(value = "slug", required = false) String assur_slug) throws IOException, DocumentException {
        log.info("PecController | displayPECBySearch is called");
        List<PECCreanceResponse> pecs = Collections.emptyList();
        List<PECDetailsResponse> pecDetails = Collections.emptyList();
        String dD = datemin+"T00:00:00";
        String dF;
        if(datemax == null){
            dF = datemin + "T23:59:59";
        }else{
            dF = datemax + "T23:59:59";
        }
        LocalDateTime dateDebut = LocalDateTime.parse(dD);
        LocalDateTime dateFin = LocalDateTime.parse(dF);
        boolean checkType = (assur_type != null && !assur_type.isBlank());
        boolean checkSlug = (assur_slug != null && !assur_slug.isBlank());
        if(checkType && checkSlug){
            pecDetails = pecService.getPECRecapByDateMinAndMaxAndAssur(dateDebut, dateFin, assur_slug);
        }else if(checkType == true && checkSlug == false){
            pecs = pecService.getPECRecapByDateMinAndMaxAndType(dateDebut, dateFin, assur_type);
        }else if(checkType == false && checkSlug == true){
            pecDetails = pecService.getPECRecapByDateMinAndMaxAndAssur(dateDebut, dateFin, assur_slug);
        }else {
            pecs = pecService.getPECRecapByDateMinAndMax(dateDebut, dateFin);
        }


        Context context = new Context();
        context.setVariable("date_debut",dateDebut.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("date_fin",dateFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        String htmlContentToRender = "";
        String outputFilename = "";
        if(checkSlug){
            double total = 0;
            double total_facture = 0;
            double total_pec = 0;
            double total_verse = 0;
            if(pecDetails.size() > 0){
                total = pecDetails.stream().mapToDouble(PECDetailsResponse::getMontant_pec).sum();
                total_facture = pecDetails.stream().mapToDouble(PECDetailsResponse::getMontant_facture).sum();
                total_pec = pecDetails.stream().mapToDouble(PECDetailsResponse::getMontant_pec).sum();
                total_verse = pecDetails.stream().mapToDouble(PECDetailsResponse::getMontant_verse).sum();
                context.setVariable("assurance", pecDetails.get(0).getAssurance());
                context.setVariable("total_activite", pecDetails.size());
                context.setVariable("recaps", pecDetails);
            }

            context.setVariable("total_creance", total);
            context.setVariable("total_facture", total_facture);
            context.setVariable("total_pec", total_pec);
            context.setVariable("total_verse", total_verse);

            htmlContentToRender = templateEngine.process("pecperiod_details", context);
            outputFilename = "pecperiod_details.pdf";
        }else {
            double total = 0;
            if(pecs.size() > 0){
                total = pecs.stream().mapToDouble(PECCreanceResponse::getMontant_pec).sum();
            }
            context.setVariable("total", total);
            context.setVariable("recaps", pecs);

            htmlContentToRender = templateEngine.process("pecperiod_all", context);
            outputFilename = "pecperiod_all.pdf";
        }

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
        headers.setContentDispositionFormData("inline", FileSystems
                .getDefault()
                .getPath("src")
                .resolve(outputFilename)
                .toUri().toURL().toString());

        Resource resource = new UrlResource(FileSystems
                .getDefault()
                .getPath("src")
                .resolve(outputFilename)
                .toUri());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}