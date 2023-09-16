package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Devise;
import com.dopediatrie.hosman.secretariat.payload.request.DeviseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DeviseResponse;

import java.util.List;

public interface DeviseService {
    List<Devise> getAllDevises();

    long addDevise(DeviseRequest deviseRequest);

    void addDevise(List<DeviseRequest> deviseRequests);

    DeviseResponse getDeviseById(long deviseId);

    void editDevise(DeviseRequest deviseRequest, long deviseId);

    public void deleteDeviseById(long deviseId);
}
