package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviseRequest {
    private String nom;
    private String code;
    private String symbol = "";
    private double taux = 0;
}