package com.dopediatrie.hosman.hospi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransfusedResponse {
    private long id;
    private LocalDateTime date_op;
    private String provenance;
    private String donneur;
    private String receveur;
    private String hemoglobine;
    private String comments;
    private String motif;
}
