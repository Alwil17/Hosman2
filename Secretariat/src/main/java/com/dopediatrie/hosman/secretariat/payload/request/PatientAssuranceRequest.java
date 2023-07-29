package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PatientAssuranceRequest {
    private long patient_id;
    private long assurance_id;
    private double taux;
    private Date date_debut;
    private Date date_fin;
}