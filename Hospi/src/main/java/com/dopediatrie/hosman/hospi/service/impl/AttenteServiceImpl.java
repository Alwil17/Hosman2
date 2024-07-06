package com.dopediatrie.hosman.hospi.service.impl;


import com.dopediatrie.hosman.hospi.payload.response.AttenteResponse;
import com.dopediatrie.hosman.hospi.service.AttenteService;
import com.dopediatrie.hosman.hospi.service.MedecinService;
import com.dopediatrie.hosman.hospi.service.SecteurService;
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
public class AttenteServiceImpl implements AttenteService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:81/attentes";

    private final SecteurService secteurService;
    private final MedecinService medecinService;
    private final String NOT_FOUND = "ATTENTE_NOT_FOUND";

    @Override
    public List<AttenteResponse> getAllAttentes() {
        List<AttenteResponse> patientResponses = Collections.emptyList();
        ResponseEntity<AttenteResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl, AttenteResponse[].class);
        patientResponses = Arrays.asList(responseEntity.getBody());
        return patientResponses;
    }

    @Override
    public AttenteResponse getAttenteById(long attenteId) {
        log.info("PatientServiceImpl | getPatientById is called");

        ResponseEntity<AttenteResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + attenteId,
                        AttenteResponse.class);

        return responseEntity.getBody();

    }

    @Override
    public AttenteResponse getAttenteByNum(long attenteNum) {
        log.info("PatientServiceImpl | getPatientById is called");

        ResponseEntity<AttenteResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/num/" + attenteNum,
                        AttenteResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public List<AttenteResponse> getAttenteFor(String vue, String medecin) {
        List<AttenteResponse> patientResponses = Collections.emptyList();
        ResponseEntity<AttenteResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?vue="+vue+"&medecin="+medecin, AttenteResponse[].class);
        patientResponses = Arrays.asList(responseEntity.getBody());
        return patientResponses;
    }


}
