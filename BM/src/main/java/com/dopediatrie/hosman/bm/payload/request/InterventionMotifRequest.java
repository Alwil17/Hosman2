package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterventionMotifRequest {
    private long intervention_id;
    private long motif_id;
}