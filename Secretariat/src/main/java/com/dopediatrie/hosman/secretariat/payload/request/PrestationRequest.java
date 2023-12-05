package com.dopediatrie.hosman.secretariat.payload.request;

import com.dopediatrie.hosman.secretariat.entity.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PrestationRequest {
    private String provenance;
    private long patient_id;
    private MedecinRequest demandeur;
    private String consulteur;
    private String secteur_code;
    private LocalDateTime date_prestation = LocalDateTime.now();

    private List<PrestationTarifRequest> tarifs;
}