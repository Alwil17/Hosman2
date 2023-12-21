package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecteurRequest {
    private String libelle;
    private String couleur;
    private String code;
}