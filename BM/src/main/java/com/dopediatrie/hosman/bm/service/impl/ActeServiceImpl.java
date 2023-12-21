package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.ICDAPIclient;
import com.dopediatrie.hosman.bm.payload.response.ActeResponse;
import com.dopediatrie.hosman.bm.payload.response.ISearchResult;
import com.dopediatrie.hosman.bm.payload.response.PatientResponse;
import com.dopediatrie.hosman.bm.service.ActeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ActeServiceImpl implements ActeService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:81/tarifs";

    @Override
    public ActeResponse getActeByCode(String code) {
        log.info("ActeServiceImpl | getActeByCode is called");
        ResponseEntity<ActeResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/code/" + code,
                        ActeResponse.class);
        return responseEntity.getBody();
    }


}
