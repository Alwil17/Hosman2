package com.dopediatrie.hosman.bm.payload.response;

import jakarta.validation.constraints.Max;
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
public class OrdonnanceResponse {
    private long id;
    private String reference;
    private String indicateur1;
    private String indicateur2;
    private boolean stocked;
    private boolean prepositionned;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private List<PrescriptionResponse> prescriptions;
}