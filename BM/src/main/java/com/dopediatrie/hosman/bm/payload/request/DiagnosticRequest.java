package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiagnosticRequest {
    private String libelle;
    private String code;
}