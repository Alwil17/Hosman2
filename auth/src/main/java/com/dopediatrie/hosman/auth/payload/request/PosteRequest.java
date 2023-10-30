package com.dopediatrie.hosman.auth.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PosteRequest {
    private String intitule;
    private String slug;
    private String code;
    private long departement_id;
}
