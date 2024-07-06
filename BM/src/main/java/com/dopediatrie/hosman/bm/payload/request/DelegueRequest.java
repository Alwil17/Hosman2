package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DelegueRequest {
    private String nom;
    private String prenoms;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;

    private LaboratoireRequest laboratoire;
}