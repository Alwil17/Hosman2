package com.dopediatrie.hosman.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PosteResponse {
    private long id;
    private String intitule;
    private String slug;
    private String code;
}
