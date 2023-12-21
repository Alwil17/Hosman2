package com.dopediatrie.hosman.bm.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ISearchResult {
    private boolean error;
    private String errorMessage;
    private String uniqueSearchId;
    List<DiagnosticResponse> destinationEntities;
}
