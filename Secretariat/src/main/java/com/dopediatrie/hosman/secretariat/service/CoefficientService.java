package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Coefficient;
import com.dopediatrie.hosman.secretariat.payload.request.CoefficientRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CoefficientResponse;

import java.util.List;

public interface CoefficientService {
    List<Coefficient> getAllCoefficients();

    long addCoefficient(CoefficientRequest coefficientRequest);

    CoefficientResponse getCoefficientById(long coefficientId);

    void editCoefficient(CoefficientRequest coefficientRequest, long coefficientId);

    public void deleteCoefficientById(long coefficientId);
}
