package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class PatientRequest {
    private String reference;
    private List<PatientMaladieRequest> maladies;
    private List<FiliationRequest> parents;

    private String commentaire;
    private AntecedantRequest antecedant;
    private CoefficientRequest coefficient;
}