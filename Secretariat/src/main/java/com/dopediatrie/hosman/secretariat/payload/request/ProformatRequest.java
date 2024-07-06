package com.dopediatrie.hosman.secretariat.payload.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProformatRequest {
    private String patient;
    private List<TarifRequest> actes;
    private int is_assure = 0;
}