package com.dopediatrie.hosman.bm.payload.request;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrdonnanceRequest {
    private String patient_ref;
    private Long consultation_id;
    @Max(value = 50)
    private String indicateur1;
    @Max(value = 50)
    private String indicateur2;
    private boolean stocked = false;
    private boolean prepositionned = false;

    private List<PrescriptionRequest> prescriptions;

}