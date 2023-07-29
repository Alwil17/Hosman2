package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InterventionTarifRequest {
    private long intervention_id;
    private long tarif_id;
}