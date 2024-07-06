package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Forme;
import com.dopediatrie.hosman.bm.payload.request.FormeRequest;
import com.dopediatrie.hosman.bm.payload.response.FormeResponse;

import java.util.List;

public interface FormeService {
    List<Forme> getAllFormes();

    long addForme(FormeRequest formeRequest);

    void addForme(List<FormeRequest> formeRequests);

    FormeResponse getFormeById(long formeId);

    void editForme(FormeRequest formeRequest, long formeId);

    public void deleteFormeById(long formeId);

    List<String> getPresentationLike(String q);

    List<String> getDosageLike(String q);

    List<String> getConditionnementLike(String q);

    void deleteAllForProduitId(long produitId);
}
