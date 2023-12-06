package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.payload.request.MedecinRequest;
import com.dopediatrie.hosman.secretariat.payload.response.MedecinResponse;
import com.dopediatrie.hosman.secretariat.service.MedecinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MedecinServiceImpl implements MedecinService {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://localhost:8085/employes";

    @Override
    public List<MedecinResponse> getAllMedecins() {
        List<MedecinResponse> medecinResponses = Collections.emptyList();
        ResponseEntity<MedecinResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl, MedecinResponse[].class);
        medecinResponses = Arrays.asList(responseEntity.getBody());
        return medecinResponses;
    }

    @Override
    public String addMedecin(MedecinRequest medecinRequest) {
        log.info("MedecinServiceImpl | addMedecin is called");
        MedecinRequest toSend = medecinRequest;
        toSend.set_medecin(true);
        if(medecinRequest.getType().equals("interne")){
            toSend.set_employe(true);
        }else {
            toSend.set_employe(false);
            toSend.set_temporaire(true);
        }

        HttpEntity<MedecinRequest> request = new HttpEntity<>(toSend);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(baseUrl+"/matricule", request, String.class);

        log.info("MedecinServiceImpl | addMedecin | Medecin Created");
        String medecinMatricule = (responseEntity.getBody() != null && !responseEntity.getBody().isBlank()) ? responseEntity.getBody() : "";
        return medecinMatricule;
    }

    @Override
    public void addMedecin(List<MedecinRequest> medecinRequests) {
        log.info("MedecinServiceImpl | addMedecin is called");
        for (MedecinRequest medecinRequest : medecinRequests) {
            MedecinRequest toSend = medecinRequest;
            toSend.set_medecin(true);
            if(medecinRequest.getType().equals("interne")){
                toSend.set_employe(true);
            }else {
                toSend.set_employe(false);
                toSend.set_temporaire(true);
                toSend.setDate_debut(LocalDateTime.now());
            }
            HttpEntity<MedecinRequest> request = new HttpEntity<>(toSend);
            restTemplate.postForEntity(baseUrl, request, Long.class);
        }

        log.info("MedecinServiceImpl | addMedecin | Medecin Created");
    }

    @Override
    public List<MedecinResponse> getMedecinByType(String typeMedecin) {
        log.info("MedecinServiceImpl | getMedecinByType is called");
        List<MedecinResponse> medecinResponses = Collections.emptyList();
        ResponseEntity<MedecinResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?type="+typeMedecin, MedecinResponse[].class);
        medecinResponses = Arrays.asList(responseEntity.getBody());
        return medecinResponses;
    }

    @Override
    public MedecinResponse getMedecinByMatricule(String matricule) {
        log.info("MedecinServiceImpl | getMedecinByMatricule is called");
        try {
            ResponseEntity<MedecinResponse[]> responseEntity = restTemplate
                    .getForEntity(baseUrl+"/search?matricule="+matricule, MedecinResponse[].class);

            if(responseEntity.getStatusCode() == HttpStatusCode.valueOf(404)){
                return new MedecinResponse();
            }
            return responseEntity.getBody()[0];
        }catch (Exception e){
            log.info(e.getMessage());
            return new MedecinResponse();
        }
    }

    @Override
    public MedecinResponse getMedecinForUser(long userId) {
        log.info("MedecinServiceImpl | getMedecinForUser is called | userId: {}", userId);
        ResponseEntity<MedecinResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/user/" + userId,
                        MedecinResponse.class);

        return responseEntity.getBody();
    }
}
