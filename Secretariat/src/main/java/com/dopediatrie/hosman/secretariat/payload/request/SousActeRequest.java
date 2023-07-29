package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SousActeRequest {
    private String libelle;
    private String code;
    private Long acte_id;
}