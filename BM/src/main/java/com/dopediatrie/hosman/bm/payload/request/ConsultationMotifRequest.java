package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultationMotifRequest {
    private long consultation_id;
    private long motif_id;
    private String caractere;
}