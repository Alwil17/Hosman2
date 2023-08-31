package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PersonneRequest {
    private String nom;
    private String prenoms;
    private String tel1;
    private String tel2 = "";
    private String email = "";
    private String type_piece = "CNI";
    private String no_piece = "";
    private String adresse = "";
}