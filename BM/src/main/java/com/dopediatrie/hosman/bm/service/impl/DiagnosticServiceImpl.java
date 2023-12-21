package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.ICDAPIclient;
import com.dopediatrie.hosman.bm.payload.response.CodeInfo;
import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;
import com.dopediatrie.hosman.bm.payload.response.ISearchResult;
import com.dopediatrie.hosman.bm.service.DiagnosticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiagnosticServiceImpl implements DiagnosticService {
    String icduri = "https://id.who.int/icd/release/11/2023-01/mms";

    @Override
    public List<DiagnosticResponse> getDiagnosticByLibelle(String libelle) {
        log.info("DiagnosticServiceImpl | getDiagnosticByLibelle is called");
        List<DiagnosticResponse> responses = Collections.emptyList();
        try {
            ICDAPIclient api = new ICDAPIclient();
            String token = api.getToken();
            ISearchResult result = api.search(token, icduri, libelle);
            if(!result.isError()){
                responses = result.getDestinationEntities();
            }else {
                throw new BMCustomException(result.getErrorMessage(), "404");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return responses;
    }

    @Override
    public DiagnosticResponse getDiagnosticByCode(String diagnostic) {
        log.info("DiagnosticServiceImpl | getDiagnosticByCode is called");
        DiagnosticResponse diagnosticResponse = new DiagnosticResponse();
        try {
            ICDAPIclient api = new ICDAPIclient();
            String token = api.getToken();
            CodeInfo code = api.getCodeInfo(token, icduri, diagnostic);
            diagnosticResponse = api.getCodeDetails(token, icduri,  code);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return diagnosticResponse;
    }
}
