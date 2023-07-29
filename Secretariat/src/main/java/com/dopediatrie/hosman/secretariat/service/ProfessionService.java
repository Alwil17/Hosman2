package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Profession;
import com.dopediatrie.hosman.secretariat.payload.request.ProfessionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ProfessionResponse;

import java.util.List;

public interface ProfessionService {
    List<Profession> getAllProfessions();

    long addProfession(ProfessionRequest patientRequest);

    ProfessionResponse getProfessionById(long patientId);

    void editProfession(ProfessionRequest patientRequest, long patientId);

    public void deleteProfessionById(long patientId);
}
