package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrestationResponse {
    private long id;
    private Patient patient;
    private String provenance;
    private Medecin demandeur;
    private Medecin consulteur;
    private Secteur secteur;
    private LocalDateTime date_prestation;
    private List<Tarif> tarifs;
    private Facture facture;
}
