package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Transfused;
import com.dopediatrie.hosman.hospi.payload.request.TransfusedRequest;
import com.dopediatrie.hosman.hospi.payload.response.TransfusedResponse;

import java.util.List;

public interface TransfusedService {
    List<Transfused> getAllTransfuseds();

    long addTransfused(TransfusedRequest medExterneRequest);

    void addTransfused(List<TransfusedRequest> medExterneRequests);

    TransfusedResponse getTransfusedById(long medExterneId);

    List<TransfusedResponse> getTransfusedByHospitId(long hospitId);

    void editTransfused(TransfusedRequest medExterneRequest, long medExterneId);

    public void deleteTransfusedById(long medExterneId);
}
