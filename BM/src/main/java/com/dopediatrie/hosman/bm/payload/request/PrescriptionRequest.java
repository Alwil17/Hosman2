package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PrescriptionRequest {
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
    private List<String> heures;
    private long produit_id;
    private long forme_id;
}