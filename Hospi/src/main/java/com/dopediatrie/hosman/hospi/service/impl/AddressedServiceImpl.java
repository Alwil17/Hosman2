package com.dopediatrie.hosman.hospi.service.impl;

import com.dopediatrie.hosman.hospi.entity.Addressed;
import com.dopediatrie.hosman.hospi.exception.HospiCustomException;
import com.dopediatrie.hosman.hospi.payload.request.AddressedRequest;
import com.dopediatrie.hosman.hospi.payload.response.AddressedResponse;
import com.dopediatrie.hosman.hospi.payload.response.MedecinResponse;
import com.dopediatrie.hosman.hospi.repository.HospitRepository;
import com.dopediatrie.hosman.hospi.repository.AddressedRepository;
import com.dopediatrie.hosman.hospi.service.AddressedService;
import com.dopediatrie.hosman.hospi.service.MedecinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class AddressedServiceImpl implements AddressedService {
    private final String NOT_FOUND = "ADDRESSED_NOT_FOUND";

    private final AddressedRepository addressedRepository;
    private final HospitRepository hospitRepository;
    private final MedecinService medecinService;

    @Override
    public List<Addressed> getAllAddresseds() {
        return addressedRepository.findAll();
    }

    @Override
    public long addAddressed(AddressedRequest addressedRequest) {
        log.info("AddressedServiceImpl | addAddressed is called");
        Addressed addressed;
        if(addressedRepository.existsByMed_refAndHospitId(addressedRequest.getMed_ref(), addressedRequest.getHospit_id())){
            addressed = addressedRepository.findByMed_refAndHospitId(addressedRequest.getMed_ref(), addressedRequest.getHospit_id()).orElseThrow();
            editAddressed(addressedRequest, addressed.getId());
        }else {
            addressed = Addressed.builder()
                    .specialite(addressedRequest.getSpecialite())
                    .med_ref(addressedRequest.getMed_ref())
                    .comments(addressedRequest.getComments())
                    .institution(addressedRequest.getInstitution())
                    .motif(addressedRequest.getMotif())
                    .medical_letter(addressedRequest.getMedical_letter())
                    .medical_report(addressedRequest.getMedical_report())
                    .transport(addressedRequest.getTransport())
                    .date_op(addressedRequest.getDate_op())
                    .hospit(hospitRepository.findById(addressedRequest.getHospit_id()).orElseThrow())
                    .build();
            addressed = addressedRepository.save(addressed);
        }

        log.info("AddressedServiceImpl | addAddressed | Addressed Created/Updated");
        log.info("AddressedServiceImpl | addAddressed | Addressed Id : " + addressed.getId());
        return addressed.getId();
    }

    @Override
    public void addAddressed(List<AddressedRequest> addressedRequests) {
        log.info("AddressedServiceImpl | addAddressed is called");

        for (AddressedRequest addressedRequest : addressedRequests) {
            Addressed addressed;
            if(addressedRepository.existsByMed_refAndHospitId(addressedRequest.getMed_ref(), addressedRequest.getHospit_id())){
                addressed = addressedRepository.findByMed_refAndHospitId(addressedRequest.getMed_ref(), addressedRequest.getHospit_id()).orElseThrow();
                editAddressed(addressedRequest, addressed.getId());
            }else {
                addressed = Addressed.builder()
                        .specialite(addressedRequest.getSpecialite())
                        .med_ref(addressedRequest.getMed_ref())
                        .comments(addressedRequest.getComments())
                        .institution(addressedRequest.getInstitution())
                        .motif(addressedRequest.getMotif())
                        .medical_letter(addressedRequest.getMedical_letter())
                        .medical_report(addressedRequest.getMedical_report())
                        .transport(addressedRequest.getTransport())
                        .date_op(addressedRequest.getDate_op())
                        .hospit(hospitRepository.findById(addressedRequest.getHospit_id()).orElseThrow())
                        .build();
                addressedRepository.save(addressed);
            }
        }

        log.info("AddressedServiceImpl | addAddressed | Addressed Created");
    }

    @Override
    public AddressedResponse getAddressedById(long addressedId) {
        log.info("AddressedServiceImpl | getAddressedById is called");
        log.info("AddressedServiceImpl | getAddressedById | Get the addressed for addressedId: {}", addressedId);

        Addressed addressed
                = addressedRepository.findById(addressedId)
                .orElseThrow(
                        () -> new HospiCustomException("Addressed with given Id not found", NOT_FOUND));

        AddressedResponse addressedResponse = new AddressedResponse();

        copyProperties(addressed, addressedResponse);
        MedecinResponse mr = medecinService.getMedecinByMatricule(addressedResponse.getMed_ref());
        addressedResponse.setMedecin(mr);

        log.info("AddressedServiceImpl | getAddressedById | addressedResponse :" + addressedResponse.toString());

        return addressedResponse;
    }

    @Override
    public List<AddressedResponse> getAddressedByHospitId(long hospitId) {
        log.info("AddressedServiceImpl | getAddressedByHospitId is called");
        List<Addressed> addresseds = addressedRepository.findAllByHospitId(hospitId);
        List<AddressedResponse> addressedResponses = new ArrayList<>();
        for (Addressed s : addresseds) {
            AddressedResponse sr = new AddressedResponse();
            copyProperties(s, sr);
            addressedResponses.add(sr);
        }
        return addressedResponses;
    }

    @Override
    public void editAddressed(AddressedRequest addressedRequest, long addressedId) {
        log.info("AddressedServiceImpl | editAddressed is called");

        Addressed addressed
                = addressedRepository.findById(addressedId)
                .orElseThrow(() -> new HospiCustomException(
                        "Addressed with given Id not found",
                        NOT_FOUND
                ));
        addressed.setSpecialite(addressedRequest.getSpecialite());
        addressed.setMed_ref(addressedRequest.getMed_ref());
        addressed.setComments(addressedRequest.getComments());
        addressed.setInstitution(addressedRequest.getInstitution());
        addressed.setMotif(addressedRequest.getMotif());
        addressed.setMedical_letter(addressedRequest.getMedical_letter());
        addressed.setMedical_report(addressedRequest.getMedical_report());
        addressed.setTransport(addressedRequest.getTransport());
        addressed.setDate_op(addressedRequest.getDate_op());
        addressed.setHospit(hospitRepository.findById(addressedRequest.getHospit_id()).orElseThrow());

        addressedRepository.save(addressed);

        log.info("AddressedServiceImpl | editAddressed | Addressed Updated");
        log.info("AddressedServiceImpl | editAddressed | Addressed Id : " + addressed.getId());
    }

    @Override
    public void deleteAddressedById(long addressedId) {
        log.info("Addressed id: {}", addressedId);

        if (!addressedRepository.existsById(addressedId)) {
            log.info("Im in this loop {}", !addressedRepository.existsById(addressedId));
            throw new HospiCustomException(
                    "Addressed with given with Id: " + addressedId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Addressed with id: {}", addressedId);
        addressedRepository.deleteById(addressedId);
    }
}
