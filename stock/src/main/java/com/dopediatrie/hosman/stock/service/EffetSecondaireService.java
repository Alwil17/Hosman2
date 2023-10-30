package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.EffetSecondaire;
import com.dopediatrie.hosman.stock.payload.request.NameRequest;
import com.dopediatrie.hosman.stock.payload.response.NameResponse;

import java.util.List;

public interface EffetSecondaireService {
    List<EffetSecondaire> getAllEffetSecondaires();

    long addEffetSecondaire(NameRequest nameRequest);

    void addEffetSecondaire(List<NameRequest> nameRequests);

    NameResponse getEffetSecondaireById(long nameId);

    void editEffetSecondaire(NameRequest nameRequest, long nameId);

    public void deleteEffetSecondaireById(long nameId);
}
