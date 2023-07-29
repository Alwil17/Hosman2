package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Adresse;
import com.dopediatrie.hosman.secretariat.payload.request.AdresseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AdresseResponse;

import java.util.List;

public interface AdresseService {
    List<Adresse> getAllAdresses();

    long addAdresse(AdresseRequest adresseRequest);

    AdresseResponse getAdresseById(long adresseId);

    void editAdresse(AdresseRequest adresseRequest, long adresseId);

    public void deleteAdresseById(long adresseId);
}
