package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Profession;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.ProfessionRequest;
import com.dopediatrie.hosman.secretariat.payload.response.ProfessionResponse;
import com.dopediatrie.hosman.secretariat.repository.ProfessionRepository;
import com.dopediatrie.hosman.secretariat.service.ProfessionService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProfessionServiceImpl implements ProfessionService {
    private final ProfessionRepository professionRepository;
    private final String NOT_FOUND = "PROFESSION_NOT_FOUND";

    @Override
    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }

    @Override
    public long addProfession(ProfessionRequest professionRequest) {
        log.info("ProfessionServiceImpl | addProfession is called");

        Profession profession
                = Profession.builder()
                .denomination(professionRequest.getDenomination())
                .slug(Str.slug(professionRequest.getDenomination()))
                .build();

        profession = professionRepository.save(profession);

        log.info("ProfessionServiceImpl | addProfession | Profession Created");
        log.info("ProfessionServiceImpl | addProfession | Profession Id : " + profession.getId());
        return profession.getId();
    }

    @Override
    public ProfessionResponse getProfessionById(long professionId) {
        log.info("ProfessionServiceImpl | getProfessionById is called");
        log.info("ProfessionServiceImpl | getProfessionById | Get the profession for professionId: {}", professionId);

        Profession profession
                = professionRepository.findById(professionId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Profession with given Id not found", NOT_FOUND));

        ProfessionResponse professionResponse = new ProfessionResponse();

        copyProperties(profession, professionResponse);

        log.info("ProfessionServiceImpl | getProfessionById | professionResponse :" + professionResponse.toString());

        return professionResponse;
    }

    @Override
    public void editProfession(ProfessionRequest professionRequest, long professionId) {
        log.info("ProfessionServiceImpl | editProfession is called");

        Profession profession
                = professionRepository.findById(professionId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Profession with given Id not found",
                        NOT_FOUND
                ));
        profession.setDenomination(professionRequest.getDenomination());
        profession.setSlug(Str.slug(professionRequest.getDenomination()));
        professionRepository.save(profession);

        log.info("ProfessionServiceImpl | editProfession | Profession Updated");
        log.info("ProfessionServiceImpl | editProfession | Profession Id : " + profession.getId());
    }

    @Override
    public void deleteProfessionById(long professionId) {
        log.info("Profession id: {}", professionId);

        if (!professionRepository.existsById(professionId)) {
            log.info("Im in this loop {}", !professionRepository.existsById(professionId));
            throw new SecretariatCustomException(
                    "Profession with given with Id: " + professionId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Profession with id: {}", professionId);
        professionRepository.deleteById(professionId);
    }
}
