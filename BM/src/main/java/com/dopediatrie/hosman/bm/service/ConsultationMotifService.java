package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.ConsultationMotif;
import com.dopediatrie.hosman.bm.entity.ConsultationMotifPK;
import com.dopediatrie.hosman.bm.payload.request.ConsultationMotifRequest;
import com.dopediatrie.hosman.bm.payload.response.ConsultationMotifResponse;

import java.util.List;

public interface ConsultationMotifService {
    List<ConsultationMotif> getAllConsultationMotifs();

    ConsultationMotifPK addConsultationMotif(ConsultationMotifRequest consultationMotifRequest);

    ConsultationMotifResponse getConsultationMotifById(long interventionMotifId);

    void editConsultationMotif(ConsultationMotifRequest consultationMotifRequest, long interventionMotifId);

    public void deleteConsultationMotifById(long interventionMotifId);

    void deleteAllForConsultationId(long consultationId);
}
