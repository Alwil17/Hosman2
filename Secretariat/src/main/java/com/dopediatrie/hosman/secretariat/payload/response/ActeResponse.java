package com.dopediatrie.hosman.secretariat.payload.response;

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
    private long structure_id;
    private long groupe_id;
}
