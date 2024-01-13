package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Sortie;
import com.dopediatrie.hosman.hospi.payload.request.SortieRequest;
import com.dopediatrie.hosman.hospi.payload.response.SortieResponse;

import java.util.List;

public interface SortieService {
    List<Sortie> getAllSorties();

    long addSortie(SortieRequest medExterneRequest);

    void addSortie(List<SortieRequest> medExterneRequests);

    SortieResponse getSortieById(long medExterneId);

    List<SortieResponse> getSortieByHospitId(long hospitId);

    void editSortie(SortieRequest medExterneRequest, long medExterneId);

    public void deleteSortieById(long medExterneId);
}
