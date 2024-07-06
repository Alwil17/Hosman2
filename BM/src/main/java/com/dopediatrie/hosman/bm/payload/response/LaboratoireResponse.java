package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoireResponse {
    private long id;
    private String nom;
    private String slug;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;
}
