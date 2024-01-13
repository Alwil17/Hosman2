package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.payload.response.ConsultationResponse;
import com.dopediatrie.hosman.hospi.payload.response.MotifResponse;
import com.dopediatrie.hosman.hospi.service.MotifService;
import com.dopediatrie.hosman.hospi.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MotifServiceImpl implements MotifService {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:84/motifs";

    @Override
    public MotifResponse getMotifByLibelle(String libelle) {
        log.info("MotifServiceImpl | getMotifByLibelle is called");

        ResponseEntity<MotifResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/libelle/" + libelle,
                        MotifResponse.class);

        return responseEntity.getBody();
    }

}
