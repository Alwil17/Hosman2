package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.ICDAPIclient;
import com.dopediatrie.hosman.bm.payload.request.ConsultationDiagnosticRequest;
import com.dopediatrie.hosman.bm.payload.response.*;
import com.dopediatrie.hosman.bm.service.DiagnosticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiagnosticServiceImpl implements DiagnosticService {
    String icduri = "https://id.who.int/icd/release/11/2023-01/mms";

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @Override
    public String getToken() {
        log.info("DiagnosticServiceImpl | getToken is called");
        String token = "";
        try {
            ICDAPIclient api = new ICDAPIclient();
            token = api.getToken();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return token;
    }

    @Override
    public List<ConsultationDiagnosticResponse> getDiagnosticCountByDateRange(LocalDateTime datemin, LocalDateTime datemax) {
        log.info("DiagnosticServiceImpl | getDiagnosticCountByDateRange is called");

        String sqlquery = "select cd.diagnostic as diagnostic, count(*) as total \n" +
                "from consultation c \n" +
                "JOIN consultation_diagnostic cd ON c.id = cd.consultation_id \n" +
                "where c.date_consultation >= '"+ datemin +"' " +
                "and c.date_consultation <= '"+ datemax +"' \n" +
                "group by cd.diagnostic \n" +
                "order by total desc ";

        List<ConsultationDiagnosticResponse> consultations = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new ConsultationDiagnosticResponse(
                resultSet.getString("diagnostic"),
                resultSet.getInt("total")
        ));

        if (consultations != null && consultations.size() > 0){
            for (ConsultationDiagnosticResponse cdr : consultations) {
                DiagnosticResponse dr = getDiagnosticByCode(cdr.getDiagnostic());
                cdr.setDiagnostic_response(dr);
            }
        }
        return consultations;
    }
}
