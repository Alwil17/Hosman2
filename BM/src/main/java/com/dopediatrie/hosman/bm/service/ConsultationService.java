package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Consultation;
import com.dopediatrie.hosman.bm.payload.request.ConsultationRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationResponse;

import java.util.List;

public interface ConsultationService {
    List<Consultation> getAllConsultations();

    long addConsultation(ConsultationRequest consultationRequest);

    ConsultationResponse getConsultationById(long interventionId);

    List<ConsultationResponse> getConsultationByPatientRef(String patientRef);

    void editConsultation(ConsultationRequest consultationRequest, long interventionId);

    public void deleteConsultationById(long interventionId);

    ConsultationResponse getConsultationByRef(String consultationRef);
}
