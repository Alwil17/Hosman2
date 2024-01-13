package com.dopediatrie.hosman.hospi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecteurRequest {
    private String libelle;
    private String couleur;
    private String code;
}