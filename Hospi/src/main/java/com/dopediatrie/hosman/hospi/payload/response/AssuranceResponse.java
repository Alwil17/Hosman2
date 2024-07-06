package com.dopediatrie.hosman.hospi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssuranceResponse {
    private long id;
    private String nom;
    private String representant;
    private String email;
    private String tel1;
    private String tel2;
    private NameResponse type_assurance;
    private List<TarifResponse> tarifs;
}
