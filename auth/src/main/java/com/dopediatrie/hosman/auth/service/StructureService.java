package com.dopediatrie.hosman.auth.service;

import com.dopediatrie.hosman.auth.entity.Structure;
import com.dopediatrie.hosman.auth.payload.request.StructureRequest;
import com.dopediatrie.hosman.auth.payload.response.StructureResponse;

import java.util.List;

public interface StructureService {
    List<Structure> getAllStructures();

    long addStructure(StructureRequest structureRequest);

    void addStructure(List<StructureRequest> structureRequests);

    StructureResponse getStructureById(long structureId);

    void editStructure(StructureRequest structureRequest, long structureId);

    public void deleteStructureById(long structureId);
}
