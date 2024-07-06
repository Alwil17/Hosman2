package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Transfered;
import com.dopediatrie.hosman.bm.payload.request.TransferedRequest;
import com.dopediatrie.hosman.bm.payload.response.TransferedResponse;

import java.util.List;

public interface TransferedService {
    List<Transfered> getAllTransfereds();

    long addTransfered(TransferedRequest transferedRequest);

    void addTransfered(List<TransferedRequest> transferedRequests);

    TransferedResponse getTransferedById(long transferedId);

    TransferedResponse getTransferedByConsultationId(long consultationId);

    void editTransfered(TransferedRequest transferedRequest, long transferedId);

    public void deleteTransferedById(long transferedId);
}
