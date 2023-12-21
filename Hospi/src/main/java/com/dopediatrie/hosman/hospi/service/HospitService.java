package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Hospit;
import com.dopediatrie.hosman.hospi.payload.request.HospitRequest;
import com.dopediatrie.hosman.hospi.payload.response.HospitResponse;

import java.util.List;

public interface HospitService {
    List<Hospit> getAllHospits();

    long addHospit(HospitRequest hospitRequest);

    void addHospit(List<HospitRequest> hospitRequests);

    HospitResponse getHospitById(long hospitId);

    void editHospit(HospitRequest hospitRequest, long hospitId);

    public void deleteHospitById(long hospitId);
}
