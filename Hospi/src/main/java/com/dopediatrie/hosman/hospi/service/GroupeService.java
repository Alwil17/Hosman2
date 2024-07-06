package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.response.GroupeResponse;

import java.util.List;

public interface GroupeService {
    List<GroupeResponse> getAllGroupes();

    GroupeResponse getGroupeById(long groupeId);
}
