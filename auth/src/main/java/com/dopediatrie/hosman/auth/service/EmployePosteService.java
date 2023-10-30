package com.dopediatrie.hosman.auth.service;

import com.dopediatrie.hosman.auth.entity.EmployePoste;
import com.dopediatrie.hosman.auth.entity.EmployePostePK;
import com.dopediatrie.hosman.auth.payload.request.EmployePosteRequest;
import com.dopediatrie.hosman.auth.payload.response.EmployePosteResponse;

import java.util.List;

public interface EmployePosteService {
    List<EmployePoste> getAllEmployePostes();

    EmployePostePK addEmployePoste(EmployePosteRequest employePosteRequest);

    EmployePosteResponse getEmployePosteById(long employePosteId);

    void editEmployePoste(EmployePosteRequest employePosteRequest, long employePosteId);

    public void deleteEmployePosteById(long employePosteId);
}
