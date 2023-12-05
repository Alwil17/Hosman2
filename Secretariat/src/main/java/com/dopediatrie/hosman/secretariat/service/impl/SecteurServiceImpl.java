package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.payload.request.SecteurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.SecteurResponse;
import com.dopediatrie.hosman.secretariat.service.SecteurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class SecteurServiceImpl implements SecteurService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://localhost:85/secteurs";

    @Override
    public List<SecteurResponse> getAllSecteurs() {
        List<SecteurResponse> secteurResponses = Collections.emptyList();
        ResponseEntity<SecteurResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?departement=DS", SecteurResponse[].class);
        secteurResponses = Arrays.asList(responseEntity.getBody());
        return secteurResponses;
    }

    @Override
    public long addSecteur(SecteurRequest secteurRequest) {
        log.info("SecteurServiceImpl | addSecteur is called");

        HttpEntity<SecteurRequest> request = new HttpEntity<>(secteurRequest);

        ResponseEntity<Long> responseEntity = restTemplate
                .postForEntity(baseUrl, request, Long.class);

        log.info("SecteurServiceImpl | addSecteur | Secteur Created");
        long secteurId = (responseEntity.getBody() != null && responseEntity.getBody() != 0) ? responseEntity.getBody() : 0;
        return secteurId;
    }

    @Override
    public void addSecteur(List<SecteurRequest> secteurRequests) {
        log.info("SecteurServiceImpl | addSecteur is called");

        for (SecteurRequest secteurRequest : secteurRequests) {
            HttpEntity<SecteurRequest> request = new HttpEntity<>(secteurRequest);
            restTemplate.postForEntity(baseUrl, request, Long.class);
        }

        log.info("SecteurServiceImpl | addSecteur | Secteur Created");
    }

    @Override
    public SecteurResponse getSecteurById(long secteurId) {
        log.info("SecteurServiceImpl | getSecteurById is called");
        log.info("SecteurServiceImpl | getSecteurById | Get the secteur for secteurId: {}", secteurId);

        ResponseEntity<SecteurResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + secteurId,
                        SecteurResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public void editSecteur(SecteurRequest secteurRequest, long secteurId) {
        log.info("SecteurServiceImpl | editSecteur is called");

        HttpEntity<SecteurRequest> request = new HttpEntity<>(secteurRequest);

        restTemplate.put(baseUrl+"/"+secteurId, request);

        log.info("SecteurServiceImpl | editSecteur | Secteur Updated");
    }

    @Override
    public void deleteSecteurById(long secteurId) {
        log.info("Secteur id: {}", secteurId);
        restTemplate.delete(baseUrl+"/"+secteurId);
    }

    @Override
    public SecteurResponse getSecteurForUser(long userId) {
        log.info("SecteurServiceImpl | getSecteurForUser is called | userId: {}", userId);

        ResponseEntity<SecteurResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/user/" + userId,
                        SecteurResponse.class);

        return responseEntity.getBody();
    }
}
