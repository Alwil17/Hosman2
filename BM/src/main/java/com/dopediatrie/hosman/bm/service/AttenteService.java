package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.payload.request.AttenteRequest;
import com.dopediatrie.hosman.bm.payload.response.AttenteResponse;

import java.util.List;

public interface AttenteService {
    List<AttenteResponse> getAllAttentes();

    long addAttente(AttenteRequest secteurRequest);

    void addAttente(List<AttenteRequest> secteurRequests);

    AttenteResponse getAttenteById(long secteurId);

    void editAttente(AttenteRequest secteurRequest, long secteurId);

    public void deleteAttenteById(long secteurId);

    AttenteResponse getAttenteForUser(long userId);

    AttenteResponse getAttenteByCode(String secteur_code);

    void updateAttenteStatus(long attenteRef, long user_id, boolean start);

    AttenteResponse getAttenteByNum(long attente_num);
}
