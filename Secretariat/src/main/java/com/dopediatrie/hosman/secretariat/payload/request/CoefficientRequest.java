package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CoefficientRequest {
    private double nbe = 0;
    private double nim = 0;
    private double nip = 0;
    private double smf = 0;
    private double mni = 0;
    private double mf = 0;
    private double pf = 0;
    private double ass = 0;
    private double imv = 0;

    private double coef = 0;
    private String commentaire;

    private long patient_id;
}