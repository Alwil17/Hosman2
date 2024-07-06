package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Stick;
import com.dopediatrie.hosman.hospi.payload.request.StickRequest;
import com.dopediatrie.hosman.hospi.payload.response.StickResponse;

import java.util.List;

public interface StickService {
    List<Stick> getAllSticks();

    long addStick(StickRequest stickRequest);

    void addStick(List<StickRequest> stickRequests);

    StickResponse getStickById(long stickId);

    void editStick(StickRequest stickRequest, long stickId);

    public void deleteStickById(long stickId);

    List<Stick> getStickByGroupe(String groupe);
}
