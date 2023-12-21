package com.dopediatrie.hosman.hospi.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HospitRequest {
    private String motif;
    private String diagnostic;
    private String hdm;
    private long patient_id;
    private long secteur_id;
    private long consultation_id;
    private LocalDateTime date_hospit;
}