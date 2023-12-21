package com.dopediatrie.hosman.hospi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosticResponse {
    private String id;
    private String title;
    private String stemId;
    private boolean hasCodingNote;
    private String chapter;
    private String theCode;
    private String description;
}
