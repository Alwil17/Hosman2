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
public class ChirurgieRequest {
    private LocalDateTime date_op;
    private String med_type;
    private String med_ref;
    private double coef;
    private String comments;
    private String title;
    private long hospit_id;
    private int frais;
}