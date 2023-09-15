package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Groupe;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.GroupeRequest;
import com.dopediatrie.hosman.secretariat.payload.response.GroupeResponse;
import com.dopediatrie.hosman.secretariat.repository.GroupeRepository;
import com.dopediatrie.hosman.secretariat.service.GroupeService;
import com.dopediatrie.hosman.secretariat.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class GroupeServiceImpl implements GroupeService {
    private final GroupeRepository groupeRepository;
    private final String NOT_FOUND = "GROUPE_NOT_FOUND";

    @Override
    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }

    @Override
    public long addGroupe(GroupeRequest groupeRequest) {
        log.info("GroupeServiceImpl | addGroupe is called");

        Groupe groupe
                = Groupe.builder()
                .libelle(groupeRequest.getLibelle())
                .slug(Str.slug(groupeRequest.getLibelle()))
                .position(groupeRequest.getPosition())
                .couleur(groupeRequest.getCouleur())
                .structure_id(groupeRequest.getStructure_id())
                .build();

        groupe = groupeRepository.save(groupe);

        log.info("GroupeServiceImpl | addGroupe | Groupe Created");
        log.info("GroupeServiceImpl | addGroupe | Groupe Id : " + groupe.getId());
        return groupe.getId();
    }

    @Override
    public void addGroupe(List<GroupeRequest> groupeRequests) {
        log.info("GroupeServiceImpl | addGroupe is called");

        for (GroupeRequest groupeRequest: groupeRequests) {
            Groupe groupe
                    = Groupe.builder()
                    .libelle(groupeRequest.getLibelle())
                    .slug(Str.slug(groupeRequest.getLibelle()))
                    .position(groupeRequest.getPosition())
                    .couleur(groupeRequest.getCouleur())
                    .structure_id(groupeRequest.getStructure_id())
                    .build();
            groupeRepository.save(groupe);
        }

        log.info("GroupeServiceImpl | addGroupe | Groupes Created");
    }

    @Override
    public GroupeResponse getGroupeById(long groupeId) {
        log.info("GroupeServiceImpl | getGroupeById is called");
        log.info("GroupeServiceImpl | getGroupeById | Get the groupe for groupeId: {}", groupeId);

        Groupe groupe
                = groupeRepository.findById(groupeId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Groupe with given Id not found", NOT_FOUND));

        GroupeResponse groupeResponse = new GroupeResponse();

        copyProperties(groupe, groupeResponse);

        log.info("GroupeServiceImpl | getGroupeById | groupeResponse :" + groupeResponse.toString());

        return groupeResponse;
    }

    @Override
    public void editGroupe(GroupeRequest groupeRequest, long groupeId) {
        log.info("GroupeServiceImpl | editGroupe is called");

        Groupe groupe
                = groupeRepository.findById(groupeId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Groupe with given Id not found",
                        NOT_FOUND
                ));
        groupe.setLibelle(groupeRequest.getLibelle());
        groupe.setSlug(Str.slug(groupeRequest.getLibelle()));
        groupe.setCouleur(groupeRequest.getCouleur());
        groupe.setPosition(groupeRequest.getPosition());
        groupe.setStructure_id(groupeRequest.getStructure_id());
        groupeRepository.save(groupe);

        log.info("GroupeServiceImpl | editGroupe | Groupe Updated");
        log.info("GroupeServiceImpl | editGroupe | Groupe Id : " + groupe.getId());
    }

    @Override
    public void deleteGroupeById(long groupeId) {
        log.info("Groupe id: {}", groupeId);

        if (!groupeRepository.existsById(groupeId)) {
            log.info("Im in this loop {}", !groupeRepository.existsById(groupeId));
            throw new SecretariatCustomException(
                    "Groupe with given with Id: " + groupeId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Groupe with id: {}", groupeId);
        groupeRepository.deleteById(groupeId);
    }
}
