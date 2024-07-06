package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.EffetSecondaire;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.response.NameResponse;

import java.util.List;

public interface EffetSecondaireService {
    List<EffetSecondaire> getAllEffetSecondaires();

    long addEffetSecondaire(NameRequest nameRequest);

    void addEffetSecondaire(List<NameRequest> nameRequests);

    NameResponse getEffetSecondaireById(long nameId);

    void editEffetSecondaire(NameRequest nameRequest, long nameId);

    public void deleteEffetSecondaireById(long nameId);

    List<String> getEffetSecondaireLike(String q);
}
