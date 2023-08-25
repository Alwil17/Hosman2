package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.PersonneAPrevenir;
import com.dopediatrie.hosman.secretariat.payload.request.PersonneAPrevenirRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PersonneAPrevenirResponse;

import java.util.List;

public interface PersonneAPrevenirService {
    List<PersonneAPrevenir> getAllPersonneAPrevenirs();

    long addPersonneAPrevenir(PersonneAPrevenirRequest patientRequest);

    PersonneAPrevenirResponse getPersonneAPrevenirById(long patientId);

    PersonneAPrevenirResponse searchByNomAndPrenoms(String nom, String prenoms);

    void editPersonneAPrevenir(PersonneAPrevenirRequest patientRequest, long patientId);

    public void deletePersonneAPrevenirById(long patientId);
}
