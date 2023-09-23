package com.dopediatrie.hosman.secretariat.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttenteResponse {
    private long id;
    private long num_attente;
    private int ordre;
    private boolean attente;
    private LocalDateTime date_attente;
    private long structure_id;
}
