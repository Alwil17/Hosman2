package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.payload.response.*;
import com.dopediatrie.hosman.hospi.service.TarifService;
import com.dopediatrie.hosman.hospi.utils.Str;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class TarifServiceImpl implements TarifService {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:81/tarifs";

    @Override
    public Map<String, List<TarifResponse>> getAllTarifs() {
        List<TarifResponse> tarifResponses = Collections.emptyList();
        List<TarifGlobalResponse> tarifGResponses = Collections.emptyList();
        Map<String, List<TarifResponse>> allTarifs = new HashMap<>();

        ResponseEntity<TarifResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl, TarifResponse[].class);
        tarifResponses = Arrays.asList(responseEntity.getBody());

        //log.info(tarifResponses);

        for (TarifResponse tarif : tarifResponses) {
            if (!allTarifs.containsKey(tarif.getActe().getGroupe().getLibelle())) {
                allTarifs.put(tarif.getActe().getGroupe().getLibelle(), new ArrayList<>());
            }
            allTarifs.get(tarif.getActe().getGroupe().getLibelle()).add(tarif);
        }

        //log.info(allTarifs);

        return allTarifs;
    }

    @Override
    public TarifResponse getTarifById(long tarifId) {
        log.info("TarifServiceImpl | getTarifById is called");

        ResponseEntity<TarifResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + tarifId,
                        TarifResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public TarifResponse getTarifByCode(String code) {
        log.info("TarifServiceImpl | getTarifByCode is called");

        ResponseEntity<TarifResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/code/" + code,
                        TarifResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public List<TarifResponse> getTarifForGroupeAndActe(String groupeCode, String acte) {
        log.info("TarifServiceImpl | getTarifForGroupeAndActe is called");
        List<TarifResponse> tarifResponses = Collections.emptyList();
        ResponseEntity<TarifResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?groupe="+groupeCode+"&acte="+acte, TarifResponse[].class);
        tarifResponses = Arrays.asList(responseEntity.getBody());
        return tarifResponses;
    }


    @Override
    public List<TarifResponse> getTarifForExamen() {
        log.info("TarifServiceImpl | getTarifForExamen is called");

        List<TarifResponse> tarifResponses = Collections.emptyList();
        ResponseEntity<TarifResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/examens", TarifResponse[].class);
        tarifResponses = Arrays.asList(responseEntity.getBody());
        return tarifResponses;
    }
}
