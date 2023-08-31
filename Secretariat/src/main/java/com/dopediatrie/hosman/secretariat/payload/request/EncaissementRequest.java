package com.dopediatrie.hosman.secretariat.payload.request;

import com.dopediatrie.hosman.secretariat.entity.ModePayement;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EncaissementRequest {
    private String provenance;
    private LocalDateTime date_encaissement;

    private List<EncaissementModeRequest> mode_payements;
}