package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Addressed;
import com.dopediatrie.hosman.bm.payload.request.AddressedRequest;
import com.dopediatrie.hosman.bm.payload.response.AddressedResponse;

import java.util.List;

public interface AddressedService {
    List<Addressed> getAllAddresseds();

    long addAddressed(AddressedRequest addressedRequest);

    void addAddressed(List<AddressedRequest> addressedRequests);

    AddressedResponse getAddressedById(long addressedId);

    AddressedResponse getAddressedByConsultationId(long consultationId);

    void editAddressed(AddressedRequest addressedRequest, long addressedId);

    public void deleteAddressedById(long addressedId);
}
