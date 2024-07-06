package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClasseRequest {
    private String nom;
    private String slug;
    private String couleur;
}