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
public class AddressedRequest {
    private LocalDateTime date_op;
    private String specialite;
    private String med_ref;
    private String comments;
    private String institution;
    private String motif;
    private long medical_letter;
    private long medical_report;
    private String transport;
    private long hospit_id;
}