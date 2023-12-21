package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.response.ConsultationResponse;

import java.util.List;

public interface ConsultationService {
    ConsultationResponse getConsultationById(long interventionId);
}
