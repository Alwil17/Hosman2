package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
public class RendezVousRequest {
    private String medecin_ref;
    private String intervenant_ref;
    private String patient_nom;
    private String patient_prenoms;
    private char patient_sexe;
    private LocalDateTime patient_naiss;
    private String date_rdv;
    private String heure_rdv;
    private String objet;
}