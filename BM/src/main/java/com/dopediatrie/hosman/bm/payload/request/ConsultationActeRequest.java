package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultationActeRequest {
    private long consultation_id;
    private String acte;
}