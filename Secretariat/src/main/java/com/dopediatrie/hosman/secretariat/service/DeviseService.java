package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Devise;
import com.dopediatrie.hosman.secretariat.payload.request.DeviseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DeviseResponse;

import java.util.List;

public interface DeviseService {
    List<Devise> getAllDevises();

    long addDevise(DeviseRequest etatRequest);

    DeviseResponse getDeviseById(long etatId);

    void editDevise(DeviseRequest etatRequest, long etatId);

    public void deleteDeviseById(long etatId);
}
