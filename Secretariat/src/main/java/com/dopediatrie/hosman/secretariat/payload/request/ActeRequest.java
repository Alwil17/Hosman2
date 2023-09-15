package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActeRequest {
    private String libelle;
    private String code;
    private long groupe_id;
    private long structure_id = 1;
}