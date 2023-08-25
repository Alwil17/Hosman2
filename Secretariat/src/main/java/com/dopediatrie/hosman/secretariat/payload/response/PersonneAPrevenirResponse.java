package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonneAPrevenirResponse {
    private long id;
    private String nom;
    private String prenoms;
    private String tel;
    private String adresse;
}
