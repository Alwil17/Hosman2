package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Employeur;
import com.dopediatrie.hosman.secretariat.payload.request.EmployeurRequest;
import com.dopediatrie.hosman.secretariat.payload.response.EmployeurResponse;

import java.util.List;

public interface EmployeurService {
    List<Employeur> getAllEmployeurs();

    long addEmployeur(EmployeurRequest assuranceRequest);

    void addEmployeur(List<EmployeurRequest> employeurRequests);

    EmployeurResponse getEmployeurById(long assuranceId);

    void editEmployeur(EmployeurRequest assuranceRequest, long assuranceId);

    public void deleteEmployeurById(long assuranceId);
}
