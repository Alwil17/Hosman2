package com.dopediatrie.hosman.auth.service;

import com.dopediatrie.hosman.auth.entity.Poste;
import com.dopediatrie.hosman.auth.payload.request.PosteRequest;
import com.dopediatrie.hosman.auth.payload.response.PosteResponse;

import java.util.List;

public interface PosteService {
    List<Poste> getAllPostes();

    long addPoste(PosteRequest posteRequest);

    void addPoste(List<PosteRequest> posteRequests);

    PosteResponse getPosteById(long posteId);

    void editPoste(PosteRequest posteRequest, long posteId);

    public void deletePosteById(long posteId);
}
