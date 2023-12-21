package com.dopediatrie.hosman.hospi.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LitRequest {
    private String nom;
    private long chambre_id;
}