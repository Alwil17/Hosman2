package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferedResponse {
    private long id;
    private LocalDateTime date_op;
    private String specialite;
    private String destination;
    private String comments;
    private String accompagne;
    private String motif;
    private String transport;

}
