package com.dopediatrie.hosman.stock.payload.request;

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
    private long agence_id;
}