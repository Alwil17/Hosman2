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
public class SuiviResponse {
    private long id;
    private String type;
    private long type_id;
    private double qte;
    private LocalDateTime apply_date;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String extras;
}
