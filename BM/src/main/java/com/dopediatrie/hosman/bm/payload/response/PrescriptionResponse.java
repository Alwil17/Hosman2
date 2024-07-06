package com.dopediatrie.hosman.bm.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {
    private long id;
    private String presentation;
    private int qte;
    private String conditionnement;
    private int dose_qte;
    private String dose;
    private String periode;
    private String adverbe;
    private int duree_qte;
    private String duree;
    private String note;
    @JsonIgnore
    private String heures;
    private List<String> heures_prise;
}
