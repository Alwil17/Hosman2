package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientAdresseRequest {
    private long patient_id;
    private long adresse_id;
}