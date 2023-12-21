package com.dopediatrie.hosman.bm.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinearizationEntity {
    @JsonProperty("@id")
    private String id;
    private LanguageSpecificText title;
    private LanguageSpecificText definition;
    private LanguageSpecificText longDefinition;
    private LanguageSpecificText codingNote;
    private String source;
    private String code;
}
