package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Personne;
import com.dopediatrie.hosman.secretariat.entity.PersonneAPrevenir;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.PersonneRequest;
import com.dopediatrie.hosman.secretariat.payload.response.PersonneResponse;
import com.dopediatrie.hosman.secretariat.repository.PersonneRepository;
import com.dopediatrie.hosman.secretariat.service.PersonneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class PersonneServiceImpl implements PersonneService {
    private final PersonneRepository personneRepository;
    private final String NOT_FOUND = "PERSONNE_NOT_FOUND";

    @Override
    public List<Personne> getAllPersonnes() {
        return personneRepository.findAll();
    }

    @Override
    public long addPersonne(PersonneRequest personneRequest) {
        log.info("PersonneServiceImpl | addPersonne is called");

        Personne personne;

        if(personneRepository.existsByNomAndPrenoms(personneRequest.getNom(), personneRequest.getPrenoms()) == null || !personneRepository.existsByNomAndPrenoms(personneRequest.getNom(), personneRequest.getPrenoms())){
            personne = Personne.builder()
                    .nom(personneRequest.getNom())
                    .prenoms(personneRequest.getPrenoms())
                    .tel1(personneRequest.getTel1())
                    .tel2(personneRequest.getTel2())
                    .type_piece(personneRequest.getType_piece())
                    .no_piece(personneRequest.getNo_piece())
                    .adresse(personneRequest.getAdresse())
                    .build();
            personne = personneRepository.save(personne);
        }else{
            personne = personneRepository.searchByNomAndPrenoms(personneRequest.getNom(), personneRequest.getPrenoms()).get();
            personne.setTel1(personneRequest.getTel1());
            personne.setTel2(personneRequest.getTel2());
            personne.setType_piece(personneRequest.getType_piece());
            personne.setNo_piece(personneRequest.getNo_piece());
            personne.setAdresse(personneRequest.getAdresse());
            personne = personneRepository.save(personne);
        }

        log.info("PersonneServiceImpl | addPersonne | Personne Created");
        log.info("PersonneServiceImpl | addPersonne | Personne Id : " + personne.getId());
        return personne.getId();
    }

    @Override
    public PersonneResponse getPersonneById(long personneId) {
        log.info("PersonneServiceImpl | getPersonneById is called");
        log.info("PersonneServiceImpl | getPersonneById | Get the personne for personneId: {}", personneId);

        Personne personne
                = personneRepository.findById(personneId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Personne with given Id not found", NOT_FOUND));

        PersonneResponse personneResponse = new PersonneResponse();

        copyProperties(personne, personneResponse);

        log.info("PersonneServiceImpl | getPersonneById | personneResponse :" + personneResponse.toString());

        return personneResponse;
    }

    @Override
    public void editPersonne(PersonneRequest personneRequest, long personneId) {
        log.info("PersonneServiceImpl | editPersonne is called");

        if(personneRepository.existsById(personneId)){
            Personne personne = personneRepository.findById(personneId)
                    .orElseThrow(() -> new SecretariatCustomException(
                            "Personne with given Id not found",
                            NOT_FOUND
                    ));
            personne.setNom(personneRequest.getNom());
            personne.setPrenoms(personneRequest.getPrenoms());
            personne.setTel1(personneRequest.getTel1());
            personne.setTel2(personneRequest.getTel2());
            personne.setType_piece(personneRequest.getType_piece());
            personne.setNo_piece(personneRequest.getNo_piece());
            personne.setAdresse(personneRequest.getAdresse());
            personneRepository.save(personne);
        }else{
            addPersonne(personneRequest);
        }
        log.info("PersonneServiceImpl | editPersonne | Personne Updated");
    }

    @Override
    public void deletePersonneById(long personneId) {
        log.info("Personne id: {}", personneId);

        if (!personneRepository.existsById(personneId)) {
            log.info("Im in this loop {}", !personneRepository.existsById(personneId));
            throw new SecretariatCustomException(
                    "Personne with given with Id: " + personneId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Personne with id: {}", personneId);
        personneRepository.deleteById(personneId);
    }
}
