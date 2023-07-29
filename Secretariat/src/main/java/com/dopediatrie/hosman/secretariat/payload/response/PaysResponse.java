package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaysResponse {
    private long id;
    private String nom;
    private String slug;
    private String nationalite;
    private String code;
    private int indicatif;
}
