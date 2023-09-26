package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaysRequest {
    private String nom;
    private String nationalite;
    private String code;
    private String indicatif;
}