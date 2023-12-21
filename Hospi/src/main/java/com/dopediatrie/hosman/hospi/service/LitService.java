package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Lit;
import com.dopediatrie.hosman.hospi.payload.request.LitRequest;
import com.dopediatrie.hosman.hospi.payload.response.LitResponse;

import java.util.List;

public interface LitService {
    List<Lit> getAllLits();

    long addLit(LitRequest litRequest);

    void addLit(List<LitRequest> litRequests);

    LitResponse getLitById(long litId);

    void editLit(LitRequest litRequest, long litId);

    public void deleteLitById(long litId);

    List<Lit> getLitByChambreId(long chambreId);

    List<Lit> getLitByNom(String nom);
}
