package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousResponse {
    private long id;
    private LocalDateTime date_rdv;
    private String medecin_ref;
    private MedecinResponse medecin;
    private String intervenant_ref;
    private MedecinResponse intervenant;
    private String patient_nom;
    private String patient_prenoms;
    private char patient_sexe;
    private LocalDateTime patient_naiss;
    private String objet;
    private EtatResponse etat;
}
