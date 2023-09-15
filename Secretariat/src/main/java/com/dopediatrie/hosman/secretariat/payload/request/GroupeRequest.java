package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupeRequest {
    private String libelle;
    private int position = 0;
    private String couleur = "white";
    private long structure_id = 1;
}