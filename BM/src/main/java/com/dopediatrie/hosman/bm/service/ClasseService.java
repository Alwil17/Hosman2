package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Classe;
import com.dopediatrie.hosman.bm.payload.request.ClasseRequest;
import com.dopediatrie.hosman.bm.payload.response.ClasseResponse;

import java.util.List;

public interface ClasseService {
    List<Classe> getAllClasses();

    long addClasse(ClasseRequest classeRequest);

    void addClasse(List<ClasseRequest> classeRequests);

    ClasseResponse getClasseById(long classeId);

    void editClasse(ClasseRequest classeRequest, long classeId);

    public void deleteClasseById(long classeId);

    List<Classe> getClasseByQueryString(String q);
}
