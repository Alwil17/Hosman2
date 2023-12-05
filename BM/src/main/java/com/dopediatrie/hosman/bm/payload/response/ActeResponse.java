package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActeResponse {
    private long id;
    private String libelle;
    private String slug;
    private String code;
    private int position = 0;
    private long structure_id;
    private long groupe_id;
}
