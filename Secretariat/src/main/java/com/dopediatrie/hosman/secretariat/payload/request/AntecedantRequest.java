package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AntecedantRequest {
    private String type;
    //adulte
    private String type_diabete;
    private boolean has_ugd;
    private boolean has_hta;
    private boolean has_asthme;
    private boolean has_drepano;
    private String type_drepano;
    private boolean has_alcool;
    private int nb_tabac;
    private String mesure_tabac;
    private String frequence_tabac;
    private int nb_medic;
    private List<String> medicaments;
    private int nb_chirurgie;
    private List<String> chirurgies;
    private int nb_hospit;
    private List<String> hospitalisations;
    private String allergies;
    private String autre;
    //enfant
    private String voie_accouch;
    private String voie_cause;
    private boolean reanime;
    private boolean scolarise;
    private String classe_scolarise;

    private long patient_id;
}