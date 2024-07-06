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
public class DecededRequest {
    private LocalDateTime date_op;
    private boolean reanimation;
    private boolean after_out;
    private String comments;
    private String motif;
    private long consultation_id;
}