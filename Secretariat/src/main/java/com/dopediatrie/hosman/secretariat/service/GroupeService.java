package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Groupe;
import com.dopediatrie.hosman.secretariat.payload.request.GroupeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.GroupeResponse;

import java.util.List;

public interface GroupeService {
    List<Groupe> getAllGroupes();

    long addGroupe(GroupeRequest groupeRequest);

    void addGroupe(List<GroupeRequest> groupeRequests);

    GroupeResponse getGroupeById(long groupeId);

    void editGroupe(GroupeRequest groupeRequest, long groupeId);

    public void deleteGroupeById(long groupeId);
}
