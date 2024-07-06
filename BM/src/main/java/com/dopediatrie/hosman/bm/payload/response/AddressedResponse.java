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
public class AddressedResponse {
    private long id;
    private LocalDateTime date_op;
    private String specialite;
    private String med_ref;
    private MedecinResponse medecin;
    private String comments;
    private String institution;
    private String motif;
    private boolean medical_letter;
    private boolean medical_report;
    private String transport;
}
