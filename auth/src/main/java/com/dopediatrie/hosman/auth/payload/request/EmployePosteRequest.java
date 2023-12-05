package com.dopediatrie.hosman.auth.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployePosteRequest {
    private String employe_matricule;
    private String poste_code;
}
