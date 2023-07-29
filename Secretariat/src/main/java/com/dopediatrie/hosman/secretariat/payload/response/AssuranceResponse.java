package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssuranceResponse {
    private long id;
    private String nom;
    private String reference;
    private String representant;
    private String email;
    private String tel1;
    private String tel2;
    private long type_assurance_id;
}
