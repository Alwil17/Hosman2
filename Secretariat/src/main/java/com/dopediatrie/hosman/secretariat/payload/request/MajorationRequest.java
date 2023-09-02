package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MajorationRequest {
    private double montant = 0;
    private String motif = "";
}