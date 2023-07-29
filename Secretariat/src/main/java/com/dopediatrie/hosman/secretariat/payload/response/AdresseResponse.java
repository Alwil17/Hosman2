package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdresseResponse {
    private long id;
    private String arrondissement;
    private String no_maison;
    private String rue;
    private String bp;
    private long ville_id;
    private long quartier_id;
}
