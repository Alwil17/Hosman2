package com.dopediatrie.hosman.stock.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormeRequest {
    private String presentation;
    private String dosage;
    private String conditionnement;
    private double prix;
    private long produit_id;
}