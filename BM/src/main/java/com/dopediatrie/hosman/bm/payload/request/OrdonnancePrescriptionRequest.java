package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrdonnancePrescriptionRequest {
    private long ordonnance_id;
    private long prescription_id;
}