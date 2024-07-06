package com.dopediatrie.hosman.secretariat.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoefficientResponse {
    private long id;
    private double nbe;
    private double nim;
    private double nip;
    private double smf;
    private double mni;
    private double mf;
    private double pf;
    private double ass;
    private double imv;

    private double coef;
    private String commentaire;
}
