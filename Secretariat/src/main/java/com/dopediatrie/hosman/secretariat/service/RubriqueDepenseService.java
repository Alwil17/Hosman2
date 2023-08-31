package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.RubriqueDepense;
import com.dopediatrie.hosman.secretariat.payload.request.NameRequest;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;

import java.util.List;

public interface RubriqueDepenseService {
    List<RubriqueDepense> getAllRubriqueDepenses();

    long addRubriqueDepense(NameRequest villeRequest);

    NameResponse getRubriqueDepenseById(long villeId);

    void editRubriqueDepense(NameRequest villeRequest, long villeId);

    public void deleteRubriqueDepenseById(long villeId);
}
