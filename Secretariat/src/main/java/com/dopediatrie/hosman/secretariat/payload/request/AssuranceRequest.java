package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssuranceRequest {
    private String nom;
    private String representant;
    private String email;
    private String tel1;
    private String tel2;
    private long type_assurance_id;
}