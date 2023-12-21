package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.ConsultationActe;
import com.dopediatrie.hosman.bm.payload.request.ConsultationActeRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationActeResponse;

import java.util.List;

public interface ConsultationActeService {
    List<ConsultationActe> getAllConsultationActes();

    long addConsultationActe(ConsultationActeRequest consultationActeRequest);

    ConsultationActeResponse getConsultationActeById(long interventionActeId);

    void editConsultationActe(ConsultationActeRequest consultationActeRequest, long interventionActeId);

    public void deleteConsultationActeById(long interventionActeId);

    void deleteAllForConsultationId(long consultationId);
}
