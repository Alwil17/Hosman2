package com.dopediatrie.hosman.stock.service;

import com.dopediatrie.hosman.stock.entity.Classe;
import com.dopediatrie.hosman.stock.payload.request.ClasseRequest;
import com.dopediatrie.hosman.stock.payload.response.ClasseResponse;

import java.util.List;

public interface ClasseService {
    List<Classe> getAllClasses();

    long addClasse(ClasseRequest classeRequest);

    void addClasse(List<ClasseRequest> classeRequests);

    ClasseResponse getClasseById(long classeId);

    void editClasse(ClasseRequest classeRequest, long classeId);

    public void deleteClasseById(long classeId);
}
