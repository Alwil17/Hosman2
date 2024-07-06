package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.payload.response.PatientResponse;
import com.dopediatrie.hosman.bm.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PatientServiceImpl implements PatientService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:81/patients";

    @Override
    public List<PatientResponse> getAllPatients() {
        List<PatientResponse> patientResponses = Collections.emptyList();
        ResponseEntity<PatientResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?departement=DS", PatientResponse[].class);
        patientResponses = Arrays.asList(responseEntity.getBody());
        return patientResponses;
    }

    @Override
    public PatientResponse getPatientById(long patientId) {
        log.info("PatientServiceImpl | getPatientById is called");
        log.info("PatientServiceImpl | getPatientById | Get the patient for patientId: {}", patientId);

        ResponseEntity<PatientResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + patientId,
                        PatientResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public PatientResponse getPatientByRef(String patientRef) {
        log.info("PatientServiceImpl | getPatientByRef is called");
        log.info("PatientServiceImpl | getPatientByRef | Get the patient for patientRef: {}", patientRef);

        PatientResponse pr = new PatientResponse();
        try {
            ResponseEntity<PatientResponse> responseEntity = restTemplate
                    .getForEntity(baseUrl + "/ref/" + patientRef,
                            PatientResponse.class);
            pr = responseEntity.getBody();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pr;
    }

    @Override
    public void deletePatientById(long patientId) {
        log.info("Patient id: {}", patientId);
        restTemplate.delete(baseUrl+"/"+patientId);
    }
}
