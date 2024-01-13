package com.dopediatrie.hosman.hospi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortieResponse {
    private long id;
    private LocalDateTime date_op;
    private String enceinte;
    private List<DiagnosticResponse> diagnostics;
}
