package com.dopediatrie.hosman.auth.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartementRequest {
    private String nom;
    private String slug;
    private String code;
    private long structure_id;
}
