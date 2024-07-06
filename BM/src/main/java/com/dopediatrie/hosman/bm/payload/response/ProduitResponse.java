package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitResponse {
    private long id;
    private String nom;
    private String dci;
    private String autre;
    private String infos;

    private AgenceResponse agence;
    private LaboratoireResponse laboratoire;
    private DelegueResponse delegue;

    private List<NameResponse2> indications;
    private List<NameResponse2> contre_indications;
    private List<NameResponse2> effet_secondaires;
    private List<FormeResponse> formes;
    private List<PosologieResponse> posologies;
    private List<ClasseResponse> classes;
    private List<NotifResponse> notifs;
}
