package com.dopediatrie.hosman.stock.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProduitRequest {
    private String code;
    private String nom;
    private String nom_officiel;
    private String dci;
    private String code_acte;
    private String autre;
    private double prix;

    private AgenceRequest agence;
    private LaboratoireRequest laboratoire;
    private DelegueRequest delegue;

    private List<NameRequest> indications;
    private List<NameRequest> contre_indications;
    private List<NameRequest> effet_secondaires;
    private List<FormeRequest> formes;
    private List<PosologieRequest> posologies;
    private List<ProduitClasseRequest> classes;
}