package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaysRequest {
    private String nom;
    private String slug;
    private String nationalite;
    private String code;
    private int indicatif;
}