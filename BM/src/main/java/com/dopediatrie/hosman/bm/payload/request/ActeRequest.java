package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActeRequest {
    private String libelle;
    private String code;
    private String groupe_code;
    private int position = 0;
    private long structure_id = 1;
}
