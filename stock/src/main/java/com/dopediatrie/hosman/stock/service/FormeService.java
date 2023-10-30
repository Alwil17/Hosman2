package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Forme;
import com.dopediatrie.hosman.stock.payload.request.FormeRequest;
import com.dopediatrie.hosman.stock.payload.response.FormeResponse;

import java.util.List;

public interface FormeService {
    List<Forme> getAllFormes();

    long addForme(FormeRequest formeRequest);

    void addForme(List<FormeRequest> formeRequests);

    FormeResponse getFormeById(long formeId);

    void editForme(FormeRequest formeRequest, long formeId);

    public void deleteFormeById(long formeId);
}
