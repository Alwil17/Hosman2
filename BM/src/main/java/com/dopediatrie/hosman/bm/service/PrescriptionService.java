package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Prescription;
import com.dopediatrie.hosman.bm.payload.request.PrescriptionRequest;
import com.dopediatrie.hosman.bm.payload.response.PrescriptionResponse;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> getAllPrescriptions();

    long addPrescription(PrescriptionRequest prescriptionRequest);

    void addPrescription(List<PrescriptionRequest> prescriptionRequests);

    PrescriptionResponse getPrescriptionById(long prescriptionId);

    void editPrescription(PrescriptionRequest prescriptionRequest, long prescriptionId);

    public void deletePrescriptionById(long prescriptionId);

    public List<String> getAllDoseString();

    public List<String> getAllPeriodeString();

    public List<String> getAllAdverbeString();

    public List<String> getAllDureeString();

    List<String> getAllMuGroupes();
}
