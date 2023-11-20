package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterventionDiagnosticRequest {
    private long intervention_id;
    private long diagnostic_id;
}