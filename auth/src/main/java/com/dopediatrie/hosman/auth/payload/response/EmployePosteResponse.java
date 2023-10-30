package com.dopediatrie.hosman.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployePosteResponse {
    private long employe_id;
    private long poste_id;
}
