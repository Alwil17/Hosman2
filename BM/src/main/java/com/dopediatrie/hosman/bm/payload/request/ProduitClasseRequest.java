package com.dopediatrie.hosman.bm.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProduitClasseRequest {
    public long produit_id;
    public long classe_id;
}