package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.FicheRecap;
import com.dopediatrie.hosman.secretariat.entity.PEC;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PECRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PECCreanceResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECDetailsResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECResponse;
import com.dopediatrie.hosman.secretariat.repository.*;
import com.dopediatrie.hosman.secretariat.service.PECService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PECServiceImpl implements PECService {
    private final String NOT_FOUND = "PEC_NOT_FOUND";

    private final PECRepository pecRepository;
    private final AssuranceRepository assuranceRepository;
    private final FactureRepository factureRepository;
    private final TarifRepository tarifRepository;
    private final PatientRepository patientRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<PEC> getAllPECs() {
        return pecRepository.findAll();
    }

    @Override
    public long addPEC(PECRequest pecRequest) {
        log.info("PECServiceImpl | addPEC is called");

        PEC pec
                = PEC.builder()
                .assurance(assuranceRepository.findById(pecRequest.getAssurance_id()).orElseThrow())
                .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                .tarif(tarifRepository.findById(pecRequest.getTarif_id()).orElseThrow())
                .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                .patient(patientRepository.findById(pecRequest.getPatient_id()).orElseThrow())
                .montant_pec(pecRequest.getMontant_pec())
                .build();

        pec = pecRepository.save(pec);

        log.info("PECServiceImpl | addPEC | PEC Created");
        log.info("PECServiceImpl | addPEC | PEC Id : " + pec.getId());
        return pec.getId();
    }

    @Override
    public void addPEC(List<PECRequest> patientRequests) {
        log.info("PECServiceImpl | addPEC is called");

        for (PECRequest pecRequest : patientRequests) {
            PEC pec
                    = PEC.builder()
                    .assurance(assuranceRepository.findById(pecRequest.getAssurance_id()).orElseThrow())
                    .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                    .tarif(tarifRepository.findById(pecRequest.getTarif_id()).orElseThrow())
                    .facture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow())
                    .patient(patientRepository.findById(pecRequest.getPatient_id()).orElseThrow())
                    .montant_pec(pecRequest.getMontant_pec())
                    .build();

            pecRepository.save(pec);
        }

        log.info("PECServiceImpl | addPEC | PEC Created");
    }

    @Override
    public PECResponse getPECById(long pecId) {
        log.info("PECServiceImpl | getPECById is called");
        log.info("PECServiceImpl | getPECById | Get the pec for pecId: {}", pecId);

        PEC pec
                = pecRepository.findById(pecId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PEC with given Id not found", NOT_FOUND));

        PECResponse pecResponse = new PECResponse();

        copyProperties(pec, pecResponse);

        log.info("PECServiceImpl | getPECById | pecResponse :" + pecResponse.toString());

        return pecResponse;
    }

    @Override
    public void editPEC(PECRequest pecRequest, long pecId) {
        log.info("PECServiceImpl | editPEC is called");

        PEC pec
                = pecRepository.findById(pecId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PEC with given Id not found",
                        NOT_FOUND
                ));
        pec.setAssurance(assuranceRepository.findById(pecRequest.getAssurance_id()).orElseThrow());
        pec.setFacture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow());
        pec.setTarif(tarifRepository.findById(pecRequest.getTarif_id()).orElseThrow());
        pec.setFacture(factureRepository.findById(pecRequest.getFacture_id()).orElseThrow());
        pec.setPatient(patientRepository.findById(pecRequest.getPatient_id()).orElseThrow());
        pec.setMontant_pec(pecRequest.getMontant_pec());
        pecRepository.save(pec);

        log.info("PECServiceImpl | editPEC | PEC Updated");
        log.info("PECServiceImpl | editPEC | PEC Id : " + pec.getId());
    }

    @Override
    public void deletePECById(long pecId) {
        log.info("PEC id: {}", pecId);

        if (!pecRepository.existsById(pecId)) {
            log.info("Im in this loop {}", !pecRepository.existsById(pecId));
            throw new SecretariatCustomException(
                    "PEC with given with Id: " + pecId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PEC with id: {}", pecId);
        pecRepository.deleteById(pecId);
    }

    @Override
    public List<PECResponse> getPECByDateMinAndMax(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("PECServiceImpl | getPECByDateMinAndMax is called");
        List<PECResponse> responses = new ArrayList<>();
        List<PEC> pecs = pecRepository.findAllByDateMinAndMax(dateDebut, dateFin);
        if(pecs != null && pecs.size()>0){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PECResponse pecResponse;
            for (PEC pec : pecs) {
                pecResponse = new PECResponse();
                pecResponse = mapper.convertValue(pec, PECResponse.class);
                responses.add(pecResponse);
            }
        }
        return responses;
    }

    @Override
    public List<PECResponse> getPECByDateMinAndMaxAndAssur(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_slug) {
        log.info("PECServiceImpl | getPECByDateMinAndMaxAndAssur is called");
        List<PECResponse> responses = new ArrayList<>();
        List<PEC> pecs = pecRepository.findAllByDateMinAndMaxAndAssur(dateDebut, dateFin, assur_slug);
        if(pecs != null && pecs.size()>0){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PECResponse pecResponse;
            for (PEC pec : pecs) {
                pecResponse = new PECResponse();
                pecResponse = mapper.convertValue(pec, PECResponse.class);
                responses.add(pecResponse);
            }
        }
        return responses;
    }

    @Override
    public List<PECResponse> getPECByDateMinAndMaxAndType(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_type) {
        log.info("PECServiceImpl | getPECByDateMinAndMaxAndType is called");
        List<PECResponse> responses = new ArrayList<>();
        List<PEC> pecs = pecRepository.findAllByDateMinAndMaxAndType(dateDebut, dateFin, Str.slug(assur_type));
        if(pecs != null && pecs.size()>0){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PECResponse pecResponse;
            for (PEC pec : pecs) {
                pecResponse = new PECResponse();
                pecResponse = mapper.convertValue(pec, PECResponse.class);
                responses.add(pecResponse);
            }
        }
        return responses;
    }

    @Override
    public List<PECResponse> getPECByDateMinAndMaxAndTypeAndSlug(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_type, String assur_slug) {
        log.info("PECServiceImpl | getPECByDateMinAndMaxAndTypeAndSlug is called");
        List<PECResponse> responses = new ArrayList<>();
        List<PEC> pecs = pecRepository.findAllByDateMinAndMaxAndTypeAndSlug(dateDebut, dateFin, Str.slug(assur_type), assur_slug);
        if(pecs != null && pecs.size()>0){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            PECResponse pecResponse;
            for (PEC pec : pecs) {
                pecResponse = new PECResponse();
                pecResponse = mapper.convertValue(pec, PECResponse.class);
                responses.add(pecResponse);
            }
        }
        return responses;
    }

    @Override
    public List<PECCreanceResponse> getPECRecapByDateMinAndMax(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("PECServiceImpl | getPECRecapByDateMinAndMax is called");
        String sqlquery = "select a.nom as nom_assur, t.nom as type_assur, SUM(p.montant_pec) as total_pec \n" +
                "from pec p \n" +
                "JOIN assurance a on p.assurance_id = a.id \n" +
                "JOIN type_assurance t on a.type_assurance_id = t.id \n" +
                "JOIN facture f on p.facture_id = f.id \n" +
                "where f.date_facture >= '"+ dateDebut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +"' \n" +
                "and f.date_facture <= '"+ dateFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +"' \n" +
                "and f.montant_pec > 0 \n" +
                "group by a.nom, t.nom";

        List<PECCreanceResponse> responses = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new PECCreanceResponse(
                resultSet.getString("nom_assur"),
                resultSet.getString("type_assur"),
                resultSet.getDouble("total_pec")
        ));
        if(responses != null && responses.size() > 0){
            return responses;
        }
        else {
            return new ArrayList<PECCreanceResponse>();
        }
    }

    @Override
    public List<PECCreanceResponse> getPECRecapByDateMinAndMaxAndType(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_type) {
        log.info("PECServiceImpl | getPECRecapByDateMinAndMax is called");
        String sqlquery = "select a.nom as nom_assur, t.nom as type_assur, SUM(p.montant_pec) as total_pec \n" +
                "from pec p \n" +
                "JOIN assurance a on p.assurance_id = a.id \n" +
                "JOIN type_assurance t on a.type_assurance_id = t.id \n" +
                "JOIN facture f on p.facture_id = f.id \n" +
                "where f.date_facture >= '"+ dateDebut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +"' \n" +
                "and f.date_facture <= '"+ dateFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +"' \n" +
                "and f.montant_pec > 0 \n" +
                "and t.slug = '"+ assur_type +"' \n" +
                "group by a.nom, t.nom";

        List<PECCreanceResponse> responses = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new PECCreanceResponse(
                resultSet.getString("nom_assur"),
                resultSet.getString("type_assur"),
                resultSet.getDouble("total_pec")
        ));
        if(responses != null && responses.size() > 0){
            return responses;
        }
        else {
            return new ArrayList<PECCreanceResponse>();
        }
    }

    @Override
    public List<PECDetailsResponse> getPECRecapByDateMinAndMaxAndAssur(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_slug) {
        log.info("PECServiceImpl | getPECRecapByDateMinAndMax is called");
        String sqlquery = "select a.nom as assurance, p.nom as nom_patient, p.prenoms as prenom_patient, \n" +
                "date_format(p.date_naissance, '%d/%m/%Y') as date_naissance, p.sexe as sexe, " +
                "date_format(f.date_facture, '%d/%m/%Y') as date_operation, \n" +
                "f.reference as no_facture, s.libelle as secteur, sum(f.total) as montant_facture, \n" +
                "sum(f.montant_pec) as montant_pec, sum(mf.montant) as montant_verse\n" +
                "from pec pe\n" +
                "JOIN assurance a on pe.assurance_id = a.id\n" +
                "JOIN type_assurance t on a.type_assurance_id = t.id\n" +
                "JOIN facture f on pe.facture_id = f.id\n" +
                "JOIN mode_facture mf on mf.facture_id = f.id\n" +
                "JOIN prestation pr on f.prestation_id = pr.id\n" +
                "JOIN patient p on pr.patient_id = p.id\n" +
                "JOIN secteur s on pr.secteur_code = s.code\n" +
                "where f.date_facture >= '"+ dateDebut.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +"' \n" +
                "and f.date_facture <= '"+ dateFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +"' \n" +
                "and f.montant_pec > 0\n" +
                "and a.slug = '"+ assur_slug +"' \n" +
                "group by a.nom, p.nom, p.prenoms, \n" +
                "p.date_naissance, p.sexe, f.date_facture,\n" +
                "f.reference \n" +
                "order by date_operation asc";

        List<PECDetailsResponse> responses = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new PECDetailsResponse(
                resultSet.getString("assurance"),
                resultSet.getString("nom_patient"),
                resultSet.getString("prenom_patient"),
                resultSet.getString("sexe"),
                resultSet.getString("date_naissance"),
                resultSet.getString("date_operation"),
                resultSet.getString("secteur"),
                resultSet.getString("no_facture"),
                resultSet.getDouble("montant_facture"),
                resultSet.getDouble("montant_pec"),
                resultSet.getDouble("montant_verse")
        ));
        if(responses != null && responses.size() > 0){
            return responses;
        }
        else {
            return new ArrayList<PECDetailsResponse>();
        }
    }
}
