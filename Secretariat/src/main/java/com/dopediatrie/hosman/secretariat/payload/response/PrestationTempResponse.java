package com.dopediatrie.hosman.secretariat.payload.response;

import com.dopediatrie.hosman.secretariat.entity.*;
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
public class PrestationTempResponse {
    private long id;
    private Patient patient;
    private String provenance;
    private String demandeur;
    private String consulteur;
    private String secteur_code;
    private LocalDateTime date_prestation;
    private List<Tarif> tarifs;
}
