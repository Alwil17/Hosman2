package com.dopediatrie.hosman.secretariat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FicheRecap {
    private String acte = "";
    private int nb_especes = 0;
    private double total_especes = 0;
    private int nb_cheque = 0;
    private double total_cheque = 0;
    private int nb_visa = 0;
    private double total_visa = 0;
    private int nb_pec = 0;
    private double total_pec = 0;
    private double montant_total = 0;

    @Override
    public String toString() {
        return "FicheRecap{" +
                "acte='" + acte + '\'' +
                ", nb_especes=" + nb_especes +
                ", total_especes=" + total_especes +
                ", nb_cheque=" + nb_cheque +
                ", total_cheque=" + total_cheque +
                ", nb_visa=" + nb_visa +
                ", total_visa=" + total_visa +
                ", nb_pec=" + nb_pec +
                ", total_pec=" + total_pec +
                ", montant_total=" + montant_total +
                '}';
    }
}