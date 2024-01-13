package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultationDiagnosticRequest {
    private long consultation_id;
    private String diagnostic;
    private String commentaire;
}