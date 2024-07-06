package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LaboratoireRequest {
    private String nom;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;

    private AgenceRequest agence;
}