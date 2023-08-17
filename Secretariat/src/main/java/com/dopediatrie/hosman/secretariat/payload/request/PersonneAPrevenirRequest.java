package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonneAPrevenirRequest {
    private String nom;
    private String prenoms;
    private String tel;
    private String adresse;
}