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
public class DecededRequest {
    private LocalDateTime date_op;
    private long reanimation;
    private long after_out;
    private String comments;
    private String motif;
    private long hospit_id;
}