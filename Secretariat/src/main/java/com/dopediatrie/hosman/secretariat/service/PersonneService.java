package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Personne;
import com.dopediatrie.hosman.secretariat.payload.request.PersonneRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PersonneResponse;

import java.util.List;

public interface PersonneService {
    List<Personne> getAllPersonnes();

    long addPersonne(PersonneRequest quartierRequest);

    PersonneResponse getPersonneById(long quartierId);

    void editPersonne(PersonneRequest quartierRequest, long quartierId);

    public void deletePersonneById(long quartierId);
}
