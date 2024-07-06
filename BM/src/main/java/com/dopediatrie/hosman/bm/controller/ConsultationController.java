package com.dopediatrie.hosman.bm.controller;

import com.dopediatrie.hosman.bm.entity.Consultation;
import com.dopediatrie.hosman.bm.payload.request.ConsultationRequest;
import com.dopediatrie.hosman.bm.payload.response.*;
import com.dopediatrie.hosman.bm.service.*;
import com.dopediatrie.hosman.bm.utils.Str;
import com.dopediatrie.hosman.bm.utils.Utils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.cglib.core.Local;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consultations")
@RequiredArgsConstructor
@Log4j2
public class ConsultationController {

    private final ConsultationService consultationService;
    private final SecteurService secteurService;
    private final AddressedService addressedService;
    private final DecededService decededService;
    private final TransferedService transferedService;
    private final RefusedService refusedService;

    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultations() {

        log.info("ConsultationController | getAllConsultations is called");
        return new ResponseEntity<>(consultationService.getAllConsultations(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addConsultation(@RequestHeader(value = "user_id", required = false) Long user_id, @RequestBody ConsultationRequest consultationRequest) {
        log.info("ConsultationController | addConsultation is called");
        long userId = (user_id != null && user_id != 0) ? user_id : 1;
        consultationRequest.setConsulteur_id(userId);
        long consultationId = consultationService.addConsultation(consultationRequest);
        return new ResponseEntity<>(consultationId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getConsultationById(@PathVariable("id") long consultationId) {

        log.info("ConsultationController | getConsultationById is called");

        log.info("ConsultationController | getConsultationById | consultationId : " + consultationId);

        ConsultationResponse consultationResponse
                = consultationService.getConsultationById(consultationId);
        return new ResponseEntity<>(consultationResponse, HttpStatus.OK);
    }

    @GetMapping("/ref/{id}")
    public ResponseEntity<ConsultationResponse> getConsultationByRef(@PathVariable("id") String consultationRef) {

        log.info("ConsultationController | getConsultationByRef is called");
        log.info("ConsultationController | getConsultationByRef | consultationId : " + consultationRef);

        ConsultationResponse consultationResponse
                = consultationService.getConsultationByRef(consultationRef);
        return new ResponseEntity<>(consultationResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ConsultationResponse>> getConsultationBySearch(@Schema(type = "string", allowableValues = {"today", "yesterday", "week"}) @RequestParam(value = "period", required = false) String period,
                                                                              @RequestParam(value = "datemin", required = false) String datemin, @RequestParam(value = "datemax", required = false) String datemax,
                                                                              @RequestParam(value = "secteur_code", required = false) String secteur, @RequestParam(value = "docteur_ref", required = false) String docteur) {
        log.info("ConsultationController | getConsultationBySearch is called");
        List<ConsultationResponse> consultations;
        LocalDateTime dateDebut;
        LocalDateTime dateFin;

        boolean checkSecteur = (secteur != null && !secteur.isBlank());
        boolean checkDocteur = (docteur != null && !docteur.isBlank());

        // déterminer la dateDebut et dateFin
        if(period != null){
            switch(period.toLowerCase(Locale.ROOT)) {
                case "yesterday":
                    dateDebut = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
                    dateFin = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
                    break;
                case "week":
                    dateDebut = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.of(Calendar.MONDAY))), LocalTime.MIN);
                    dateFin = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.of(Calendar.SUNDAY))), LocalTime.MAX);
                    break;
                default:
                    dateDebut = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                    dateFin = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
                    break;
            }
        }
        else {
            if(datemin != null){
                String dD = datemin+"T00:00:00";
                String dF;
                if(datemax == null){
                    dF = datemin + "T23:59:59";
                }else{
                    dF = datemax + "T23:59:59";
                }
                dateDebut = LocalDateTime.parse(dD);
                dateFin = LocalDateTime.parse(dF);
            }else {
                dateDebut = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                dateFin = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            }
        }

        // à ce stade, dateDebut et dateFin sont déjà déterminés
        if(checkSecteur && checkDocteur){
            consultations = consultationService.getConsultationByDateRangeAndSecteurAndDocteur(dateDebut, dateFin, secteur, docteur);
        }else if(checkSecteur == true && checkDocteur == false){
            consultations = consultationService.getConsultationByDateRangeAndSecteur(dateDebut, dateFin, secteur);
        }else if(checkSecteur == false && checkDocteur == true){
            consultations = consultationService.getConsultationByDateRangeAndDocteur(dateDebut, dateFin, docteur);
        }else {
            consultations = consultationService.getConsultationByDateRange(dateDebut, dateFin);
        }



        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<Resource> generateReportBySearch(@Schema(type = "string", allowableValues = {"today", "yesterday", "week"}) @RequestParam(value = "period", required = false) String period,
                                                            @RequestParam(value = "datemin", required = false) String datemin, @RequestParam(value = "datemax", required = false) String datemax,
                                                            @RequestParam(value = "secteur_code", required = false) String secteur, @RequestParam(value = "docteur_ref", required = false) String docteur,
                                                           @Schema(type = "string", allowableValues = {"all", "acte", "general"}) @RequestParam(value = "vue", required = false) String vue) throws MalformedURLException {
        log.info("ConsultationController | getConsultationBySearch is called");
        List<ConsultationResponse> consultations;
        List<ConsultationActeReportResponse> consultation_actes = new ArrayList<ConsultationActeReportResponse>();

        List<ConsultationReportResponse> all_consultations = new ArrayList<ConsultationReportResponse>();
        List<ConsultationActeReportResponse> all_consultation_actes = new ArrayList<ConsultationActeReportResponse>();
        LocalDateTime dateDebut;
        LocalDateTime dateFin;
        String reportPath;
        String outfilename;



        boolean checkSecteur = (secteur != null && !secteur.isBlank());
        boolean checkDocteur = (docteur != null && !docteur.isBlank());
        String for_type = getForType(checkSecteur, checkDocteur);
        String for_text = getForText(checkSecteur, checkDocteur, secteur, docteur);
        // déterminer la dateDebut et dateFin
        if(period != null){
            switch(period.toLowerCase(Locale.ROOT)) {
                case "yesterday":
                    dateDebut = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
                    dateFin = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
                    break;
                case "week":
                    dateDebut = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.of(Calendar.MONDAY))), LocalTime.MIN);
                    dateFin = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.of(Calendar.SUNDAY))), LocalTime.MAX);
                    break;
                default:
                    dateDebut = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                    dateFin = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
                    break;
            }
        }
        else {
            if(datemin != null){
                String dD = datemin+"T00:00:00";
                String dF;
                if(datemax == null){
                    dF = datemin + "T23:59:59";
                }else{
                    dF = datemax + "T23:59:59";
                }
                dateDebut = LocalDateTime.parse(dD);
                dateFin = LocalDateTime.parse(dF);
            }else {
                dateDebut = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                dateFin = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            }
        }

        // à ce stade, dateDebut et dateFin sont déjà déterminés
        if(vue.equals("general")){
            if(checkSecteur && checkDocteur){
                consultations = consultationService.getConsultationByDateRangeAndSecteurAndDocteur(dateDebut, dateFin, secteur, docteur);
            }else if(checkSecteur == true && checkDocteur == false){
                consultations = consultationService.getConsultationByDateRangeAndSecteur(dateDebut, dateFin, secteur);
            }else if(checkSecteur == false && checkDocteur == true){
                consultations = consultationService.getConsultationByDateRangeAndDocteur(dateDebut, dateFin, docteur);
            }else {
                consultations = consultationService.getConsultationByDateRange(dateDebut, dateFin);
            }

            if(consultations != null && consultations.size() > 0){
                for (ConsultationResponse cr : consultations) {
                    all_consultations.add(cr.toConsultationReport());
                }
            }
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("for_type", for_type);
            parameters.put("for_text", for_text);
            reportPath = "/com/dopediatrie/hosman/bm/reports/consultations_all_periode.jrxml";
            outfilename = "consultations_all";
            buildCompileReport(all_consultations, reportPath, outfilename, dateDebut, dateFin, parameters);
        }
        else{
            if(checkSecteur && checkDocteur){
                consultation_actes = consultationService.getConsultationActeByDateRangeAndSecteurAndDocteur(dateDebut, dateFin, secteur, docteur);
            }else if(checkSecteur == true && checkDocteur == false){
                consultation_actes = consultationService.getConsultationActeByDateRangeAndSecteur(dateDebut, dateFin, secteur);
            }else if(checkSecteur == false && checkDocteur == true){
                consultation_actes = consultationService.getConsultationActeByDateRangeAndDocteur(dateDebut, dateFin, docteur);
            }else {
                consultation_actes = consultationService.getConsultationActeByDateRange(dateDebut, dateFin);
            }

            // Map pour stocker la référence et le montant associé
            Map<String, Double> referenceMontantMap = new HashMap<>();

            // Parcourir la liste des consultations
            for (ConsultationActeReportResponse carr : consultation_actes) {
                if(carr.getRef_facture() == null ) carr.setMontant_facture(0);
                if(carr.getMontant_facture() <= 0) carr.setMontant_facture(0);
                // Si la référence n'existe pas dans la map
                if (!referenceMontantMap.containsKey(carr.getRef_facture())) {
                    // Ajouter la référence et son montant associé à la map
                    referenceMontantMap.put(carr.getRef_facture(), carr.getMontant_facture());
                } else {
                    // Si la référence existe déjà, affecter 0 au montant
                    carr.setMontant_facture(0);
                }
                all_consultation_actes.add(carr);
            }

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("total_consultations", all_consultation_actes.stream().mapToInt(ConsultationActeReportResponse::getTotal).sum());
            parameters.put("total_facture", all_consultation_actes.stream().mapToDouble(ConsultationActeReportResponse::getMontant_facture).sum());
            reportPath = "/com/dopediatrie/hosman/bm/reports/consultations_actes_periode.jrxml";
            outfilename = "consultation_actes";
            parameters.put("for_type", for_type);
            parameters.put("for_text", for_text);

            buildCompileReport(all_consultation_actes, reportPath, outfilename, dateDebut, dateFin, parameters);
        }

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

    @GetMapping("/report-jaunes")
    public ResponseEntity<Resource> generateReportJaunesBySearch(@RequestParam("datemin") String datemin, @RequestParam(value = "datemax", required = false) String datemax,
                                                           @RequestParam(value = "secteur_code", required = false) String secteur, @RequestParam(value = "docteur_ref", required = false) String docteur,
                                                                 @RequestParam(value = "motif_id", required = false) Long motif, @RequestParam(value = "diagnostic_code", required = false) String diagnostic,
                                                                 @RequestParam(value = "acte_code", required = false) String acte, @RequestParam("vue") String vue) throws MalformedURLException {
        log.info("ConsultationController | generateReportJaunesBySearch is called");
        List<ConsultationResponse> consultations = new ArrayList<ConsultationResponse>();

        List<ConsultationReportResponse> all_consultations = new ArrayList<ConsultationReportResponse>();
        LocalDateTime dateDebut;
        LocalDateTime dateFin;
        String reportPath;
        String outfilename;


        boolean checkSecteur = (secteur != null && !secteur.isBlank());
        boolean checkDocteur = (docteur != null && !docteur.isBlank());
        String for_type = getForType(checkSecteur, checkDocteur);
        String for_text = getForText(checkSecteur, checkDocteur, secteur, docteur);
        // déterminer la dateDebut et dateFin
        if(datemin != null){
            String dD = datemin+"T00:00:00";
            String dF;
            if(datemax == null){
                dF = datemin + "T23:59:59";
            }else{
                dF = datemax + "T23:59:59";
            }
            dateDebut = LocalDateTime.parse(dD);
            dateFin = LocalDateTime.parse(dF);
        }else {
            dateDebut = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            dateFin = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        }

        // à ce stade, dateDebut et dateFin sont déjà déterminés
        switch (vue){
            case "secteur":
                consultations = consultationService.getConsultationByDateRangeAndSecteur(dateDebut, dateFin, secteur);
                break;
            case "docteur":
                consultations = consultationService.getConsultationByDateRangeAndDocteur(dateDebut, dateFin, docteur);
                break;
            case "multi":
                consultations = consultationService.getConsultationByDateRangeAndMulti(dateDebut, dateFin, acte, motif, diagnostic);
                break;
            default:
                break;
        }

        if(consultations != null && consultations.size() > 0){
            //log.info(consultations.size());
            //log.info(consultations.get(0).toString());
            for (ConsultationResponse cr : consultations) {
                //log.info(consultations.get(0).toConsultationReport().toString());
                all_consultations.add(cr.toConsultationReport());
            }
        }

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("for_type", for_type);
        parameters.put("for_text", for_text);
        reportPath = "/com/dopediatrie/hosman/bm/reports/consultations_par_criteres.jrxml";
        outfilename = "consultations_criteres";
        buildCompileReport(all_consultations, reportPath, outfilename, dateDebut, dateFin, parameters);

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


    private String getForType(boolean checkSecteur, boolean checkDocteur){
        String forType = "";
        if(checkSecteur && checkDocteur){
            forType = "Docteur :";
        }else if(checkSecteur == true && checkDocteur == false){
            forType = "Secteur :";
        }else if(checkSecteur == false && checkDocteur == true){
            forType = "Docteur :";
        }else {
            forType = "Acte - Motif - Diagnostic";
        }
        return forType;
    }

    private String getForText(boolean checkSecteur, boolean checkDocteur, String sectorCode, String docteur_ref){
        String forText = "";
        if(checkSecteur && checkDocteur){
            forText = docteur_ref+ " ("+secteurService.getSecteurByCode(sectorCode).getLibelle()+")";
        }else if(checkSecteur == true && checkDocteur == false){
            forText = secteurService.getSecteurByCode(sectorCode).getLibelle();
        }else if(checkSecteur == false && checkDocteur == true){
            forText = docteur_ref;
        }else {
            forText = "";
        }
        return forText;
    }

    private void buildCompileReport(List datas, String reportPath, String outfilename, LocalDateTime dateDebut, LocalDateTime dateFin, Map<String, Object> additionalParams){
        try {
            InputStream logo = getClass().getResourceAsStream("/com/dopediatrie/hosman/bm/images/logo.png");

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
            if(additionalParams != null) parameters.putAll(additionalParams);

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

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<ConsultationResponse>> getConsultationByPatientRef(@PathVariable("id") String patient_ref) {
        log.info("ConsultationController | getConsultationByPatientRef is called");

        List<ConsultationResponse> consultationResponses
                = consultationService.getConsultationByPatientRef(patient_ref);

        return new ResponseEntity<>(consultationResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}/addressed")
    public ResponseEntity<AddressedResponse> getAddressedByConsultationId(@PathVariable("id") long consultationId) {
        log.info("ConsultationController | getAddressedByConsultationId is called");

        AddressedResponse addressed
                = addressedService.getAddressedByConsultationId(consultationId);
        return new ResponseEntity<>(addressed, HttpStatus.OK);
    }

    @GetMapping("/{id}/transfered")
    public ResponseEntity<TransferedResponse> getTransferedByConsultationId(@PathVariable("id") long consultationId) {
        log.info("ConsultationController | getTransferedByConsultationId is called");

        TransferedResponse transfered
                = transferedService.getTransferedByConsultationId(consultationId);
        return new ResponseEntity<>(transfered, HttpStatus.OK);
    }

    @GetMapping("/{id}/refused")
    public ResponseEntity<RefusedResponse> getRefusedByConsultationId(@PathVariable("id") long consultationId) {
        log.info("ConsultationController | getRefusedByConsultationId is called");

        RefusedResponse refused
                = refusedService.getRefusedByConsultationId(consultationId);
        return new ResponseEntity<>(refused, HttpStatus.OK);
    }

    @GetMapping("/{id}/deceded")
    public ResponseEntity<DecededResponse> getDecededByConsultationId(@PathVariable("id") long consultationId) {
        log.info("ConsultationController | getConsultationById is called");
        log.info("ConsultationController | getConsultationById | consultationId : " + consultationId);

        DecededResponse deceded
                = decededService.getDecededByConsultationId(consultationId);
        return new ResponseEntity<>(deceded, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> editConsultation(@RequestBody ConsultationRequest consultationRequest,
            @PathVariable("id") long consultationId) {

        log.info("ConsultationController | editConsultation is called");

        log.info("ConsultationController | editConsultation | consultationId : " + consultationId);

        consultationService.editConsultation(consultationRequest, consultationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteConsultationById(@PathVariable("id") long consultationId) {
        consultationService.deleteConsultationById(consultationId);
    }
}