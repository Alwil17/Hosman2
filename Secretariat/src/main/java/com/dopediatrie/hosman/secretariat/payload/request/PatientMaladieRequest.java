package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientMaladieRequest {
    private long patient_id;
    private String maladie;
}