package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdresseRequest {
    private String arrondissement = "";
    private String no_maison = "";
    private String rue = "";
    private String bp = "";
    private long ville_id;
    private long quartier_id;
}