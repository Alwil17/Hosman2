package com.dopediatrie.hosman.hospi.payload.request;

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
public class SortieRequest {
    private LocalDateTime date_op;
    private String enceinte;
    private long hospit_id;
    private List<SortieDiagnosticRequest> diagnostics;
}