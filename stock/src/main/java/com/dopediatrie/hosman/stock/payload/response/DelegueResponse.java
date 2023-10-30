package com.dopediatrie.hosman.stock.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DelegueResponse {
    private long id;
    private String nom;
    private String prenoms;
    private String tel1;
    private String tel2;
    private String email;
    private String adresse;
}
