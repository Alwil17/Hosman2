package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Reliquat;
import com.dopediatrie.hosman.secretariat.payload.request.ReliquatRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ReliquatResponse;

import java.util.List;

public interface ReliquatService {
    List<Reliquat> getAllReliquats();

    long addReliquat(ReliquatRequest reliquatRequest);

    ReliquatResponse getReliquatById(long reliquatId);

    void editReliquat(ReliquatRequest reliquatRequest, long reliquatId);

    public void deleteReliquatById(long reliquatId);
}
