package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeurRequest {
    private String nom;
    private String tel1;
    private String tel2;
    private String email;
}