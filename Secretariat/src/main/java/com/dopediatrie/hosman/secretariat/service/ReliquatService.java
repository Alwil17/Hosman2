package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Reliquat;
import com.dopediatrie.hosman.secretariat.payload.request.ReliquatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ReliquatResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ReliquatService {
    List<Reliquat> getAllReliquats();

    long addReliquat(ReliquatRequest reliquatRequest);

    ReliquatResponse getReliquatById(long reliquatId);

    List<Reliquat> getReliquatByDateMinAndMax(LocalDateTime datemin, LocalDateTime datemax);

    List<Reliquat> getReliquatByDateMinAndMaxAndNom(LocalDateTime datemin, LocalDateTime datemax, String nom);

    List<Reliquat> getReliquatByDateMinAndMaxAndReference(LocalDateTime datemin, LocalDateTime datemax, String reference);

    void editReliquat(ReliquatRequest reliquatRequest, long reliquatId);

    public void deleteReliquatById(long reliquatId);
}
