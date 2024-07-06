package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDiagnosticResponse {
    private long id;
    private long consultation_id;
    private String diagnostic;
    private DiagnosticResponse diagnostic_response;
    private int total;

    public ConsultationDiagnosticResponse(String diagnostic, int total) {
        this.diagnostic = diagnostic;
        this.total = total;
    }
}
