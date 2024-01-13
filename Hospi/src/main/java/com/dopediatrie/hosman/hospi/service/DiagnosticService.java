package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.response.DiagnosticResponse;

import java.util.List;

public interface DiagnosticService {
    DiagnosticResponse getDiagnosticByCode(String diagnostic);
}
