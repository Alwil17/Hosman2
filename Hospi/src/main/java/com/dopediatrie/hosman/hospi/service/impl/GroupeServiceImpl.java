package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.payload.response.GroupeResponse;
import com.dopediatrie.hosman.hospi.service.GroupeService;
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
public class GroupeServiceImpl implements GroupeService {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:81/groupes";

    @Override
    public List<GroupeResponse> getAllGroupes() {
        List<GroupeResponse> tarifResponses = Collections.emptyList();
        ResponseEntity<GroupeResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl, GroupeResponse[].class);
        tarifResponses = Arrays.asList(responseEntity.getBody());
        return tarifResponses;
    }

    @Override
    public GroupeResponse getGroupeById(long groupeId) {
        log.info("GroupeServiceImpl | getGroupeById is called");

        ResponseEntity<GroupeResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + groupeId,
                        GroupeResponse.class);

        return responseEntity.getBody();
    }
}
