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
public class AntecedantResponse {
    private long id;
    private String type;
    private boolean has_diabete;
    private String type_diabete;
    private boolean has_ugd;
    private boolean has_hta;
    private boolean has_asthme;
    private boolean has_drepano;
    private String type_drepano;
    private boolean has_alcool;
    private boolean has_tabac;
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
}
