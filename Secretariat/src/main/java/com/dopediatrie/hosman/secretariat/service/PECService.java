package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PEC;
import com.dopediatrie.hosman.secretariat.payload.request.PECRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PECCreanceResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECDetailsResponse;
import com.dopediatrie.hosman.secretariat.payload.response.PECResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PECService {
    List<PEC> getAllPECs();

    long addPEC(PECRequest pecRequest);

    void addPEC(List<PECRequest> pecRequests);

    PECResponse getPECById(long pecId);

    void editPEC(PECRequest pecRequest, long pecId);

    public void deletePECById(long pecId);

    List<PECResponse> getPECByDateMinAndMax(LocalDateTime dateDebut, LocalDateTime dateFin);

    List<PECResponse> getPECByDateMinAndMaxAndAssur(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_slug);

    List<PECResponse> getPECByDateMinAndMaxAndType(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_type);

    List<PECResponse> getPECByDateMinAndMaxAndTypeAndSlug(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_type, String assur_slug);

    List<PECCreanceResponse> getPECRecapByDateMinAndMax(LocalDateTime dateDebut, LocalDateTime dateFin);

    List<PECCreanceResponse> getPECRecapByDateMinAndMaxAndType(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_type);

    List<PECDetailsResponse> getPECRecapByDateMinAndMaxAndAssur(LocalDateTime dateDebut, LocalDateTime dateFin, String assur_slug);
}
