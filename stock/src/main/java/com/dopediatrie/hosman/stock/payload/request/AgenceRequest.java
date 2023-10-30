package com.dopediatrie.hosman.stock.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgenceRequest {
    private String nom;
    private String directeur;
    private String email;
    private String tel1;
    private String tel2;
    private String adresse;
}