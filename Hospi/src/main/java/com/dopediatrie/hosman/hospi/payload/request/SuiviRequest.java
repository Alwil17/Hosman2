package com.dopediatrie.hosman.hospi.payload.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SuiviRequest {
    private String type;
    private long type_id;
    private double qte;
    private LocalDateTime apply_date;
    private long hospit_id;
}