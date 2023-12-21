package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Suivi;
import com.dopediatrie.hosman.hospi.payload.request.SuiviRequest;
import com.dopediatrie.hosman.hospi.payload.response.SuiviResponse;

import java.util.List;

public interface SuiviService {
    List<Suivi> getAllSuivis();

    long addSuivi(SuiviRequest suiviRequest);

    void addSuivi(List<SuiviRequest> suiviRequests);

    SuiviResponse getSuiviById(long suiviId);

    void editSuivi(SuiviRequest suiviRequest, long suiviId);

    public void deleteSuiviById(long suiviId);
}
