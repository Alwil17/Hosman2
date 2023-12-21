package com.dopediatrie.hosman.hospi.payload.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
public class ChambreRequest {
    private String nom;
}