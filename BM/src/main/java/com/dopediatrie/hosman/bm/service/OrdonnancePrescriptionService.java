package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.OrdonnancePrescription;
import com.dopediatrie.hosman.bm.entity.OrdonnancePrescriptionPK;
import com.dopediatrie.hosman.bm.payload.request.OrdonnancePrescriptionRequest;
import com.dopediatrie.hosman.bm.payload.response.OrdonnancePrescriptionResponse;

import java.util.List;

public interface OrdonnancePrescriptionService {
    List<OrdonnancePrescription> getAllOrdonnancePrescriptions();

    OrdonnancePrescriptionPK addOrdonnancePrescription(OrdonnancePrescriptionRequest ordonnancePrescriptionRequest);

    OrdonnancePrescriptionResponse getOrdonnancePrescriptionById(long interventionPrescriptionId);

    void editOrdonnancePrescription(OrdonnancePrescriptionRequest ordonnancePrescriptionRequest, long interventionPrescriptionId);

    public void deleteOrdonnancePrescriptionById(long ordonnancePrescriptionId);

    void deleteAllForOrdonnanceId(long ordonnanceId);
}
