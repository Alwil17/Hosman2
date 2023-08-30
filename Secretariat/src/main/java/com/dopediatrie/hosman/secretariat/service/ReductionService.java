package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Reduction;
import com.dopediatrie.hosman.secretariat.payload.request.ReductionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ReductionResponse;

import java.util.List;

public interface ReductionService {
    List<Reduction> getAllReductions();

    long addReduction(ReductionRequest reductionRequest);

    ReductionResponse getReductionById(long reductionId);

    void editReduction(ReductionRequest reductionRequest, long reductionId);

    public void deleteReductionById(long reductionId);
}
