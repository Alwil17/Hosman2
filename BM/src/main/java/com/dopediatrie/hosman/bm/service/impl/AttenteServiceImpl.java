package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.payload.request.AttenteRequest;
import com.dopediatrie.hosman.bm.payload.response.AttenteResponse;
import com.dopediatrie.hosman.bm.payload.response.ISearchResult;
import com.dopediatrie.hosman.bm.service.AttenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

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

    @Override
    public List<AttenteResponse> getAllAttentes() {
        List<AttenteResponse> attenteResponses = Collections.emptyList();
        ResponseEntity<AttenteResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?departement=DS", AttenteResponse[].class);
        attenteResponses = Arrays.asList(responseEntity.getBody());
        return attenteResponses;
    }

    @Override
    public long addAttente(AttenteRequest attenteRequest) {
        log.info("AttenteServiceImpl | addAttente is called");

        HttpEntity<AttenteRequest> request = new HttpEntity<>(attenteRequest);

        ResponseEntity<Long> responseEntity = restTemplate
                .postForEntity(baseUrl, request, Long.class);

        log.info("AttenteServiceImpl | addAttente | Attente Created");
        long attenteId = (responseEntity.getBody() != null && responseEntity.getBody() != 0) ? responseEntity.getBody() : 0;
        return attenteId;
    }

    @Override
    public void addAttente(List<AttenteRequest> attenteRequests) {
        log.info("AttenteServiceImpl | addAttente is called");

        for (AttenteRequest attenteRequest : attenteRequests) {
            HttpEntity<AttenteRequest> request = new HttpEntity<>(attenteRequest);
            restTemplate.postForEntity(baseUrl, request, Long.class);
        }

        log.info("AttenteServiceImpl | addAttente | Attente Created");
    }

    @Override
    public AttenteResponse getAttenteById(long attenteId) {
        log.info("AttenteServiceImpl | getAttenteById is called");
        log.info("AttenteServiceImpl | getAttenteById | Get the attente for attenteId: {}", attenteId);

        ResponseEntity<AttenteResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + attenteId,
                        AttenteResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public void editAttente(AttenteRequest attenteRequest, long attenteId) {
        log.info("AttenteServiceImpl | editAttente is called");

        HttpEntity<AttenteRequest> request = new HttpEntity<>(attenteRequest);

        restTemplate.put(baseUrl+"/"+attenteId, request);

        log.info("AttenteServiceImpl | editAttente | Attente Updated");
    }

    @Override
    public void deleteAttenteById(long attenteId) {
        log.info("Attente id: {}", attenteId);
        restTemplate.delete(baseUrl+"/"+attenteId);
    }

    @Override
    public AttenteResponse getAttenteForUser(long userId) {
        log.info("AttenteServiceImpl | getAttenteForUser is called | userId: {}", userId);

        ResponseEntity<AttenteResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/user/" + userId,
                        AttenteResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public AttenteResponse getAttenteByCode(String attente_code) {
        log.info("AttenteServiceImpl | getAttenteByCode is called");
        try {
            ResponseEntity<AttenteResponse[]> responseEntity = restTemplate
                    .getForEntity(baseUrl + "/search?code=" + attente_code,
                            AttenteResponse[].class);

            if(responseEntity.getStatusCode() == HttpStatusCode.valueOf(404)){
                return new AttenteResponse();
            }
            return responseEntity.getBody()[0];
        }catch (Exception e){
            log.error(e.getMessage());
            return new AttenteResponse();
        }
    }

    @Override
    public void updateAttenteStatus(long attenteRef, long user_id, boolean start) {
        log.info("AttenteServiceImpl | updateAttenteStatus is called");

        AttenteRequest attenteRequest = AttenteRequest.builder().en_cours(start).build();
        //HttpEntity<AttenteRequest> request = new HttpEntity<>(attenteRequest);
        //restTemplate.put(baseUrl+"/"+attenteRef+"/updateStatus", request);

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                .baseUrl(baseUrl+"/"+attenteRef+"/updateStatus")
                .build();

        webClient.put()
                .uri("")
                .header("user_id", String.valueOf(user_id))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(attenteRequest), AttenteRequest.class)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(AttenteRequest.class);
                    } else if (response.statusCode().is4xxClientError()) {
                        return Mono.just("Error response");
                    } else {
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                });
        log.info("AttenteServiceImpl | updateAttenteStatus | Attente Updated");
    }

    @Override
    public AttenteResponse getAttenteByNum(long attente_num) {
        log.info("AttenteServiceImpl | getAttenteById is called");
        log.info("AttenteServiceImpl | getAttenteById | Get the attente for attenteNum: {}", attente_num);

        ResponseEntity<AttenteResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/num/" + attente_num,
                        AttenteResponse.class);

        return responseEntity.getBody();
    }
}
