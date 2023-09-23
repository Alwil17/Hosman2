package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Attente;
import com.dopediatrie.hosman.secretariat.payload.request.AttenteRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AttenteResponse;

import java.util.List;

public interface AttenteService {
    List<Attente> getAllAttentes();

    long addAttente(AttenteRequest attenteRequest);

    AttenteResponse getAttenteById(long attenteId);

    void editAttente(AttenteRequest attenteRequest, long attenteId);

    public void deleteAttenteById(long attenteId);
}
