package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECCreanceResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECDetailsResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECResponse;
import com.dopediatrie.hosman.secretariat.service.MedecinService;
import com.dopediatrie.hosman.secretariat.service.PECService;
import com.dopediatrie.hosman.secretariat.utils.Utils;
import com.lowagie.text.DocumentException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
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
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/factures/pecs")
@RequiredArgsConstructor
@Log4j2
public class PecController {

    private final PECService pecService;
    private final MedecinService medecinService;

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
    @GetMapping("/medecin")
    public ResponseEntity<List<PECDetailsResponse>> getPECForMedecin(@RequestParam(value = "datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax,
                                                              @Schema(type = "string", description = "Matricule du medecin") @RequestParam("ref") String medecin_ref) {
        log.info("PecController | getPECForMedecin is called");
        List<PECDetailsResponse> pecs = Collections.emptyList();
        String dD = datemin+"T00:00:00";
        String dF;
        if(datemax == null){
            dF = datemin + "T23:59:59";
        }else{
            dF = datemax + "T23:59:59";
        }
        LocalDateTime dateDebut = LocalDateTime.parse(dD);
        LocalDateTime dateFin = LocalDateTime.parse(dF);

        pecs = pecService.getPECByDateMinAndMaxAndMedecin(dateDebut, dateFin, medecin_ref);

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

    @GetMapping("/print-med")
    public ResponseEntity<Resource> displayPECByMedecin(@RequestParam(value = "datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax,
                                                        @Schema(type = "string", description = "Matricule du medecin") @RequestParam("ref") String medecin_ref)  throws IOException {
        log.info("PecController | displayPECByMedecin is called");
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

        pecDetails = pecService.getPECByDateMinAndMaxAndMedecin(dateDebut, dateFin, medecin_ref);

        String reportPath = "/com/dopediatrie/hosman/secretariat/reports/pec_per_medecin.jrxml";
        String outfilename = "pec_per_medecin";


        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("for_type", "Medecin :");
        MedecinResponse medecin = medecinService.getMedecinByMatricule(medecin_ref);
        StringBuilder sb = new StringBuilder();
        sb.append("Dr. ");
        sb.append(medecin.getNom());
        sb.append(" ");
        sb.append(medecin.getPrenoms());
        parameters.put("for_text", sb.toString());
        //System.out.println(pecDetails.size());
        buildCompileReport(pecDetails, reportPath, outfilename, dateDebut, dateFin, parameters);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(outfilename+".pdf", FileSystems
                .getDefault()
                .getPath("src")
                .resolve(outfilename+".pdf")
                .toUri().toURL().toString());

        Resource resource = new UrlResource(FileSystems
                .getDefault()
                .getPath("src")
                .resolve(outfilename+".pdf")
                .toUri());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    private void buildCompileReport(List datas, String reportPath, String outfilename, LocalDateTime dateDebut, LocalDateTime dateFin, Map<String, Object>... additionalParams){
        try {
            InputStream logo = getClass().getResourceAsStream("/com/dopediatrie/hosman/secretariat/images/logo.png");

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(datas);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("logo", logo);
            parameters.put("logo1", logo);
            parameters.put("consultations", itemsJRBean);
            parameters.put("datemin", Date
                    .from(dateDebut.atZone(ZoneId.systemDefault())
                            .toInstant()));
            parameters.put("datemax", Date
                    .from(dateFin.atZone(ZoneId.systemDefault())
                            .toInstant()));
            if(additionalParams != null && additionalParams.length >0) parameters.putAll(additionalParams[0]);

            InputStream input = getClass().getResourceAsStream(reportPath);

            JasperDesign jasperDesign = JRXmlLoader.load(input);
            /*compiling jrxml with help of JasperReport class*/
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


            /* Using jasperReport object to generate PDF */
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /*call jasper engine to display report in jasperviewer window*/
            //JasperViewer.viewReport(jasperPrint, false);


            /* outputStream to create PDF */
            String fileFinal = outfilename+".pdf";
            OutputStream outputStream = new FileOutputStream(new File("src//"+fileFinal));


            /* Write content to PDF file */
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}