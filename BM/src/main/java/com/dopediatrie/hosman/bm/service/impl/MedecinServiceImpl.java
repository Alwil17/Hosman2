package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.payload.response.MedecinResponse;
import com.dopediatrie.hosman.bm.service.MedecinService;
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

    private String baseUrl = "http://hosman-apps.com:8085/employes";

    @Override
    public List<MedecinResponse> getAllMedecins() {
        List<MedecinResponse> medecinResponses = Collections.emptyList();
        ResponseEntity<MedecinResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl, MedecinResponse[].class);
        medecinResponses = Arrays.asList(responseEntity.getBody());
        return medecinResponses;
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
