package com.dopediatrie.hosman.auth.service;

import com.dopediatrie.hosman.auth.entity.Employe;
import com.dopediatrie.hosman.auth.payload.request.EmployeRequest;
import com.dopediatrie.hosman.auth.payload.response.EmployeResponse;

import java.util.List;

public interface EmployeService {
    List<Employe> getAllEmployes();

    long addEmploye(EmployeRequest employeRequest);

    String addEmployeGetMatricule(EmployeRequest employeRequest);

    void addEmploye(List<EmployeRequest> employeRequests);

    EmployeResponse getEmployeById(long employeId);

    void editEmploye(EmployeRequest employeRequest, long employeId);

    public void deleteEmployeById(long employeId);

    List<Employe> getEmployeByType(String type);

    List<Employe> getEmployeByMatricule(String matricule);

    EmployeResponse getEmployeByUserId(long userId);
}
