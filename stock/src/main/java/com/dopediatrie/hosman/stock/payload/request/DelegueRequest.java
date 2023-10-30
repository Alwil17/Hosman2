package com.dopediatrie.hosman.stock.payload.request;

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
    private long laboratoire_id;
}