package com.dopediatrie.hosman.auth.service;

import com.dopediatrie.hosman.auth.entity.Departement;
import com.dopediatrie.hosman.auth.payload.request.DepartementRequest;
import com.dopediatrie.hosman.auth.payload.response.DepartementResponse;

import java.util.List;

public interface DepartementService {
    List<Departement> getAllDepartements();

    long addDepartement(DepartementRequest departementRequest);

    void addDepartement(List<DepartementRequest> departementRequests);

    DepartementResponse getDepartementById(long departementId);

    void editDepartement(DepartementRequest departementRequest, long departementId);

    public void deleteDepartementById(long departementId);
}
