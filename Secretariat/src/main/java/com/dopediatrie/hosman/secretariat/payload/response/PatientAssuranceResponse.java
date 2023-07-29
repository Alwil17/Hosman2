package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientAssuranceResponse {
    private long id;
    private long patient_id;
    private long assurance_id;
    private double taux;
    private Date date_debut;
    private Date date_fin;
}
