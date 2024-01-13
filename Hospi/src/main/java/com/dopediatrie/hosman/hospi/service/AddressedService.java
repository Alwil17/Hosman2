package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Addressed;
import com.dopediatrie.hosman.hospi.payload.request.AddressedRequest;
import com.dopediatrie.hosman.hospi.payload.response.AddressedResponse;

import java.util.List;

public interface AddressedService {
    List<Addressed> getAllAddresseds();

    long addAddressed(AddressedRequest medExterneRequest);

    void addAddressed(List<AddressedRequest> medExterneRequests);

    AddressedResponse getAddressedById(long medExterneId);

    List<AddressedResponse> getAddressedByHospitId(long hospitId);

    void editAddressed(AddressedRequest medExterneRequest, long medExterneId);

    public void deleteAddressedById(long medExterneId);
}
