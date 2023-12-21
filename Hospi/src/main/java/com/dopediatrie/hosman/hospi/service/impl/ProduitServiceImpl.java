package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.payload.response.ProduitResponse;
import com.dopediatrie.hosman.hospi.service.ProduitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProduitServiceImpl implements ProduitService {
    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl = "http://hosman-apps.com:83/produits";

    @Override
    public List<ProduitResponse> getAllProduits() {
        List<ProduitResponse> produitResponses = Collections.emptyList();
        ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?code_acte=medic", ProduitResponse[].class);
        produitResponses = Arrays.asList(responseEntity.getBody());
        return produitResponses;
    }

    @Override
    public ProduitResponse getProduitById(long produitId) {
        log.info("ProduitServiceImpl | getProduitById is called");
        log.info("ProduitServiceImpl | getProduitById | Get the produit for produitId: {}", produitId);

        ResponseEntity<ProduitResponse> responseEntity = restTemplate
                .getForEntity(baseUrl + "/" + produitId,
                        ProduitResponse.class);

        return responseEntity.getBody();
    }

    @Override
    public ProduitResponse getProduitByCode(String produit_code) {
        log.info("ProduitServiceImpl | getProduitByCode is called");
        try {
            ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                    .getForEntity(baseUrl + "/search?code=" + produit_code,
                            ProduitResponse[].class);

            if(responseEntity.getStatusCode() == HttpStatusCode.valueOf(404)){
                return new ProduitResponse();
            }
            return responseEntity.getBody()[0];
        }catch (Exception e){
            log.error(e.getMessage());
            return new ProduitResponse();
        }
    }

    @Override
    public List<ProduitResponse> getProduitByType(String type) {
        log.info("ProduitServiceImpl | getProduitByType is called");
        List<ProduitResponse> produitResponses = Collections.emptyList();
        ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl + "/search?code_acte=" + type,
                        ProduitResponse[].class);

        produitResponses = Arrays.asList(responseEntity.getBody());
        return produitResponses;
    }
}
