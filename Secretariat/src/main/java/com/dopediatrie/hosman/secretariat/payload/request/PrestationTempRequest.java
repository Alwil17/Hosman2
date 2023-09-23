package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PrestationTempRequest {
    private String provenance;
    private long patient_id;
    private MedecinRequest demandeur;
    private long consulteur_id;
    private long secteur_id;
    private LocalDateTime date_prestation = LocalDateTime.now();

    private List<PrestationTarifTempRequest> tarifs;
}