package com.dopediatrie.hosman.stock.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PosologieRequest {
    private String libelle;
    private String slug;
    private String type;
    private long produit_id;
}