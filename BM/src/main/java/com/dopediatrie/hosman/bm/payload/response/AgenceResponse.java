package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgenceResponse {
    private long id;
    private String nom;
    private String slug;
    private String directeur;
    private String email;
    private String tel1;
    private String tel2;
    private String adresse;
}
