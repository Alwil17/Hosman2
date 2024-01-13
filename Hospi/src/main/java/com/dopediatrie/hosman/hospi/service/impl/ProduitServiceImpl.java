package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Chambre;
import com.dopediatrie.hosman.hospi.payload.response.*;
import com.dopediatrie.hosman.hospi.service.ChambreService;
import com.dopediatrie.hosman.hospi.service.ProduitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    private String tarifBaseUrl = "http://hosman-apps.com:81/tarifs";

    private final ChambreService chambreService;

    @Override
    public List<GlobalProduitResponse> getAllProduits() {
        List<GlobalProduitResponse> responses = new ArrayList<GlobalProduitResponse>();
        List<ProduitResponse> produitResponses = new ArrayList<>();
        ResponseEntity<ProduitResponse[]> responseEntity = restTemplate
                .getForEntity(baseUrl+"/search?code_acte=medic", ProduitResponse[].class);
        produitResponses = Arrays.asList(responseEntity.getBody());
        GlobalProduitResponse response = new GlobalProduitResponse();
        response.setType("medic");
        response.setName("Médicaments");
        response.setData(produitResponses);
        responses.add(response);

        //consomables
        responseEntity = restTemplate.getForEntity(baseUrl+"/search?code_acte=conso", ProduitResponse[].class);
        //produitResponses.clear();
        produitResponses = Arrays.asList(responseEntity.getBody());
        response = new GlobalProduitResponse();
        response.setType("conso");
        response.setName("Consommables");
        response.setData(produitResponses);
        responses.add(response);
        //consomables
        responseEntity = restTemplate.getForEntity(baseUrl+"/search?code_acte=solut", ProduitResponse[].class);
        //produitResponses.clear();
        produitResponses = Arrays.asList(responseEntity.getBody());
        response = new GlobalProduitResponse();
        response.setType("solut");
        response.setName("Solutés");
        response.setData(produitResponses);
        responses.add(response);
        // examens
        ResponseEntity<TarifResponse[]> responseEntity1 = restTemplate
                .getForEntity(tarifBaseUrl+"/examens", TarifResponse[].class);

        List<TarifResponse> tarifResponses = Arrays.asList(responseEntity1.getBody());
        response = new GlobalProduitResponse();
        response.setType("examens");
        response.setName("Examens");
        response.setData(tarifResponses);
        responses.add(response);


        //chambres
        List<Chambre> chambres = chambreService.getAllChambres();
        response = new GlobalProduitResponse();
        response.setType("chambres");
        response.setName("Chambres");
        response.setData(chambres);
        responses.add(response);


        return responses;
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
