package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.PersonneAPrevenir;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PersonneAPrevenirRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PersonneAPrevenirResponse;
import com.dopediatrie.hosman.secretariat.repository.PersonneAPrevenirRepository;
import com.dopediatrie.hosman.secretariat.service.PersonneAPrevenirService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PersonneAPrevenirServiceImpl implements PersonneAPrevenirService {
    private final PersonneAPrevenirRepository personneAPrevenirRepository;
    private final String NOT_FOUND = "PERSONNE_A_PREVENIR_NOT_FOUND";

    @Override
    public List<PersonneAPrevenir> getAllPersonneAPrevenirs() {
        return personneAPrevenirRepository.findAll();
    }

    @Override
    public long addPersonneAPrevenir(PersonneAPrevenirRequest personneAPrevenirRequest) {
        log.info("PersonneAPrevenirServiceImpl | addPersonneAPrevenir is called");

        PersonneAPrevenir personneAPrevenir
                = PersonneAPrevenir.builder()
                .nom(personneAPrevenirRequest.getNom())
                .prenoms(personneAPrevenirRequest.getPrenoms())
                .tel(personneAPrevenirRequest.getTel())
                .adresse(personneAPrevenirRequest.getAdresse())
                .build();

        personneAPrevenir = personneAPrevenirRepository.save(personneAPrevenir);

        log.info("PersonneAPrevenirServiceImpl | addPersonneAPrevenir | PersonneAPrevenir Created");
        log.info("PersonneAPrevenirServiceImpl | addPersonneAPrevenir | PersonneAPrevenir Id : " + personneAPrevenir.getId());
        return personneAPrevenir.getId();
    }

    @Override
    public PersonneAPrevenirResponse getPersonneAPrevenirById(long personneAPrevenirId) {
        log.info("PersonneAPrevenirServiceImpl | getPersonneAPrevenirById is called");
        log.info("PersonneAPrevenirServiceImpl | getPersonneAPrevenirById | Get the personneAPrevenir for personneAPrevenirId: {}", personneAPrevenirId);

        PersonneAPrevenir personneAPrevenir
                = personneAPrevenirRepository.findById(personneAPrevenirId)
                .orElseThrow(
                        () -> new SecretariatCustomException("PersonneAPrevenir with given Id not found", NOT_FOUND));

        PersonneAPrevenirResponse personneAPrevenirResponse = new PersonneAPrevenirResponse();

        copyProperties(personneAPrevenir, personneAPrevenirResponse);

        log.info("PersonneAPrevenirServiceImpl | getPersonneAPrevenirById | personneAPrevenirResponse :" + personneAPrevenirResponse.toString());

        return personneAPrevenirResponse;
    }

    @Override
    public void editPersonneAPrevenir(PersonneAPrevenirRequest personneAPrevenirRequest, long personneAPrevenirId) {
        log.info("PersonneAPrevenirServiceImpl | editPersonneAPrevenir is called");

        PersonneAPrevenir personneAPrevenir
                = personneAPrevenirRepository.findById(personneAPrevenirId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "PersonneAPrevenir with given Id not found",
                        NOT_FOUND
                ));
        personneAPrevenir.setNom(personneAPrevenirRequest.getNom());
        personneAPrevenir.setPrenoms(personneAPrevenirRequest.getPrenoms());
        personneAPrevenir.setTel(personneAPrevenirRequest.getTel());
        personneAPrevenir.setAdresse(personneAPrevenirRequest.getAdresse());
        personneAPrevenirRepository.save(personneAPrevenir);

        log.info("PersonneAPrevenirServiceImpl | editPersonneAPrevenir | PersonneAPrevenir Updated");
        log.info("PersonneAPrevenirServiceImpl | editPersonneAPrevenir | PersonneAPrevenir Id : " + personneAPrevenir.getId());
    }

    @Override
    public void deletePersonneAPrevenirById(long personneAPrevenirId) {
        log.info("PersonneAPrevenir id: {}", personneAPrevenirId);

        if (!personneAPrevenirRepository.existsById(personneAPrevenirId)) {
            log.info("Im in this loop {}", !personneAPrevenirRepository.existsById(personneAPrevenirId));
            throw new SecretariatCustomException(
                    "PersonneAPrevenir with given with Id: " + personneAPrevenirId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting PersonneAPrevenir with id: {}", personneAPrevenirId);
        personneAPrevenirRepository.deleteById(personneAPrevenirId);
    }
}
