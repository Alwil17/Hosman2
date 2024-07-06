package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Consultation;
import com.dopediatrie.hosman.bm.payload.request.ConsultationRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationActeReportResponse;
import com.dopediatrie.hosman.bm.payload.response.ConsultationResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationService {
    List<Consultation> getAllConsultations();

    long addConsultation(ConsultationRequest consultationRequest);

    ConsultationResponse getConsultationById(long interventionId);

    List<ConsultationResponse> getConsultationByPatientRef(String patientRef);

    void editConsultation(ConsultationRequest consultationRequest, long interventionId);

    public void deleteConsultationById(long interventionId);

    ConsultationResponse getConsultationByRef(String consultationRef);

    List<ConsultationResponse> getConsultationByDateRange(LocalDateTime datemin, LocalDateTime datemax);

    List<ConsultationActeReportResponse> getConsultationActeByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    List<ConsultationResponse> getConsultationByDateRangeAndSecteurAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur, String docteur);

    List<ConsultationActeReportResponse> getConsultationActeByDateRangeAndSecteurAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur, String docteur);

    List<ConsultationResponse> getConsultationByDateRangeAndSecteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur);

    List<ConsultationActeReportResponse> getConsultationActeByDateRangeAndSecteur(LocalDateTime dateDebut, LocalDateTime dateFin, String secteur);

    List<ConsultationResponse> getConsultationByDateRangeAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String docteur);

    List<ConsultationActeReportResponse> getConsultationActeByDateRangeAndDocteur(LocalDateTime dateDebut, LocalDateTime dateFin, String docteur);

    List<ConsultationResponse> getConsultationByDateRangeAndMulti(LocalDateTime dateDebut, LocalDateTime dateFin, String acte, Long motif, String diagnostic);
}
