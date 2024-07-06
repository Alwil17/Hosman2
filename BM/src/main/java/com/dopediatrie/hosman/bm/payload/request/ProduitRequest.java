package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProduitRequest {
    private String nom;
    private String dci;
    private String infos;
    private String autre;

    private AgenceRequest agence;
    private LaboratoireRequest laboratoire;
    private DelegueRequest delegue;

    private List<NameRequest> indications;
    private List<NameRequest> contre_indications;
    private List<NameRequest> effet_secondaires;
    private List<FormeRequest> formes;
    private List<PosologieRequest> posologies;
    private List<ClasseRequest> classes;
}