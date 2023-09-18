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
    private long demandeur_id;
    private long consulteur_id;
    private long secteur_id;
    private LocalDateTime date_prestation = LocalDateTime.now();


}