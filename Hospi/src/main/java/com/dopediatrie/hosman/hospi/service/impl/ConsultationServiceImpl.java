package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.payload.response.ConsultationResponse;
import com.dopediatrie.hosman.hospi.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:84/consultations";

    @Override
    public ConsultationResponse getConsultationById(long consultationId) {
        log.info("ConsultationServiceImpl | getConsultationById is called");
        log.info("ConsultationServiceImpl | getConsultationById | Get the consultation for consultationId: {}", consultationId);

        ResponseEntity<ConsultationResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + consultationId,
                        ConsultationResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public ConsultationResponse getConsultationByRef(String consultationRef) {
        log.info("ConsultationServiceImpl | getConsultationByRef is called");
        log.info("ConsultationServiceImpl | getConsultationByRef | Get the consultation for consultationRef: {}", consultationRef);

        ResponseEntity<ConsultationResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/ref/" + consultationRef,
                        ConsultationResponse.class);

        return responseEntity.getBody();
    }
}
