package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.payload.response.DiagnosticResponse;
import com.dopediatrie.hosman.hospi.service.DiagnosticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiagnosticServiceImpl implements DiagnosticService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:84/diagnostics";

    @Override
    public DiagnosticResponse getDiagnosticByCode(String diagnostic) {
        log.info("DiagnosticServiceImpl | getDiagnosticByCode is called");

        ResponseEntity<DiagnosticResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + diagnostic,
                        DiagnosticResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public String getToken() {
        log.info("DiagnosticServiceImpl | getToken is called");

        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(baseUrl + "/token",
                        String.class);

        return responseEntity.getBody();
    }
}
