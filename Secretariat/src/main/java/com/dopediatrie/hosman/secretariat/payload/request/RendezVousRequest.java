package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RendezVousRequest {
    private String medecin;
    private String intervenant;
    private long patient_id;
    private String date_rdv;
    private String heure_rdv;
    private String objet;
}