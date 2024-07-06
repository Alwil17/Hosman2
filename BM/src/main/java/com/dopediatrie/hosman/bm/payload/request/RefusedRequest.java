package com.dopediatrie.hosman.bm.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefusedRequest {
    private LocalDateTime date_op;
    private String motif;
    private boolean has_decharge;
    private boolean has_ordonnance;
    private String comments;
    private long consultation_id;
}