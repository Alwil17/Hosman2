package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupeResponse {
    private long id;
    private String libelle;
    private String slug;
    private int position;
    private String couleur;
    private long structure_id;
}
