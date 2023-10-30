package com.dopediatrie.hosman.stock.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PosologieResponse {
    private long id;
    private String libelle;
    private String slug;
    private String type;
}
