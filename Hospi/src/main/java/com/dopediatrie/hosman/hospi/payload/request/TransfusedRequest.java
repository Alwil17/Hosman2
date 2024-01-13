package com.dopediatrie.hosman.hospi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusedRequest {
    private LocalDateTime date_op;
    private String provenance;
    private String donneur_ref;
    private String receveur_ref;
    private String hemoglobine;
    private String comments;
    private String motif;
    private long hospit_id;
}