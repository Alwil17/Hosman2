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
public class TransferedRequest {
    private LocalDateTime date_op;
    private String specialite;
    private String destination;
    private String comments;
    private String accompagne;
    private String motif;
    private String transport;
    private long consultation_id;
}