package com.dopediatrie.hosman.auth.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployePosteRequest {
    private long employe_id;
    private long poste_id;
}
