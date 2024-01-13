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
public class MedExterneResponse {
    private long id;
    private String type_op;
    private LocalDateTime date_op;
    private String comments;
    private String med_ref;
    private MedecinResponse medecin;
}
