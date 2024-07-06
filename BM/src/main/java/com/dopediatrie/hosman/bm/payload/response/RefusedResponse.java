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
public class RefusedResponse {
    private long id;
    private LocalDateTime date_op;
    private String motif;
    private boolean has_decharge;
    private boolean has_ordonnance;
    private String comments;
}
