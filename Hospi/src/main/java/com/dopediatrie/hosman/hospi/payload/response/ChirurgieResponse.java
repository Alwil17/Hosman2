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
public class ChirurgieResponse {
    private long id;
    private LocalDateTime date_op;
    private String med_type;
    private String med_ref;
    private MedecinResponse medecin;
    private double coef;
    private String comments;
    private String title;
    private int frais;
}
