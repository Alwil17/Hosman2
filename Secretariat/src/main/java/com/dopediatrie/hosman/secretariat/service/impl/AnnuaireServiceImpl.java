package com.dopediatrie.hosman.secretariat.service.impl;

import com.dopediatrie.hosman.secretariat.entity.Annuaire;
import com.dopediatrie.hosman.secretariat.entity.Categorie;
import com.dopediatrie.hosman.secretariat.exception.SecretariatCustomException;
import com.dopediatrie.hosman.secretariat.payload.request.AnnuaireRequest;
import com.dopediatrie.hosman.secretariat.payload.response.AnnuaireResponse;
import com.dopediatrie.hosman.secretariat.payload.response.NameResponse;
import com.dopediatrie.hosman.secretariat.repository.AnnuaireRepository;
import com.dopediatrie.hosman.secretariat.service.AnnuaireService;
import com.dopediatrie.hosman.secretariat.service.CategorieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class AnnuaireServiceImpl implements AnnuaireService {
    private final String NOT_FOUND = "ANNUAIRE_NOT_FOUND";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final AnnuaireRepository annuaireRepository;
    private final CategorieService categorieService;

    @Override
    public List<Annuaire> getAllAnnuaires() {
        log.info("AnnuaireServiceImpl | getAllAnnuaires is called");
        List<Annuaire> annuaireList = new ArrayList<>();
        List<Annuaire> annuaires = annuaireRepository.findAll();

        String sqlquery = "SELECT e.nom as nom, e.prenoms as prenom, p.denomination as profession,\n" +
                "s.libelle as secteur, e.tel1 as tel1, e.tel2 as tel2, e.email as email, \n" +
                "e.domicile as domicile, e.bureau as bureau, e.bip as bip, e.no_poste as no_poste \n" +
                "FROM employe e \n" +
                "JOIN profession p on p.id = e.profession_id\n" +
                "JOIN secteur s on s.id = e.secteur_id ";

        List<AnnuaireResponse> responses = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new AnnuaireResponse(
                0,
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("profession"),
                resultSet.getString("secteur"),
                resultSet.getString("bureau"),
                resultSet.getString("tel1"),
                resultSet.getString("tel2"),
                resultSet.getString("domicile"),
                resultSet.getString("email"),
                resultSet.getString("bip"),
                resultSet.getString("no_poste"),
                "pisjo"
        ));

        for (AnnuaireResponse employe : responses) {
            Annuaire annuaire = Annuaire.builder().build();
            copyProperties(employe, annuaire);
            annuaireList.add(annuaire);
        }

        for (Annuaire ann : annuaires) {
            NameResponse cat = categorieService.getCategorieBySlug(ann.getCategorie_slug());
            ann.setCategorie(cat);
            annuaireList.add(ann);
        }

        return annuaireList;
    }

    @Override
    public long addAnnuaire(AnnuaireRequest annuaireRequest) {
        log.info("AnnuaireServiceImpl | addAnnuaire is called");

        String categorie = categorieService.addCategorieGetSlug(annuaireRequest.getCategorie());

        Annuaire annuaire
                = Annuaire.builder()
                .nom(annuaireRequest.getNom())
                .prenom(annuaireRequest.getPrenom())
                .profession(annuaireRequest.getProfession())
                .secteur(annuaireRequest.getSecteur())
                .bureau(annuaireRequest.getBureau())
                .tel1(annuaireRequest.getTel1())
                .tel2(annuaireRequest.getTel2())
                .domicile(annuaireRequest.getDomicile())
                .email(annuaireRequest.getEmail())
                .bip(annuaireRequest.getBip())
                .no_poste(annuaireRequest.getNo_poste())
                .build();
        annuaire.setCategorie_slug(categorie);
        annuaire = annuaireRepository.save(annuaire);

        log.info("AnnuaireServiceImpl | addAnnuaire | Annuaire Created");
        log.info("AnnuaireServiceImpl | addAnnuaire | Annuaire Id : " + annuaire.getId());
        return annuaire.getId();
    }

    @Override
    public void addAnnuaire(List<AnnuaireRequest> annuaireRequests) {
        log.info("AnnuaireServiceImpl | addAnnuaire is called");

        for (AnnuaireRequest annuaireRequest : annuaireRequests) {
            String categorie = categorieService.addCategorieGetSlug(annuaireRequest.getCategorie());

            Annuaire annuaire
                    = Annuaire.builder()
                    .nom(annuaireRequest.getNom())
                    .prenom(annuaireRequest.getPrenom())
                    .profession(annuaireRequest.getProfession())
                    .secteur(annuaireRequest.getSecteur())
                    .bureau(annuaireRequest.getBureau())
                    .tel1(annuaireRequest.getTel1())
                    .tel2(annuaireRequest.getTel2())
                    .domicile(annuaireRequest.getDomicile())
                    .email(annuaireRequest.getEmail())
                    .bip(annuaireRequest.getBip())
                    .no_poste(annuaireRequest.getNo_poste())
                    .build();
            annuaire.setCategorie_slug(categorie);
            annuaireRepository.save(annuaire);
        }

        log.info("AnnuaireServiceImpl | addAnnuaire | Annuaires Created");
    }

    @Override
    public AnnuaireResponse getAnnuaireById(long annuaireId) {
        log.info("AnnuaireServiceImpl | getAnnuaireById is called");
        log.info("AnnuaireServiceImpl | getAnnuaireById | Get the annuaire for annuaireId: {}", annuaireId);

        Annuaire annuaire
                = annuaireRepository.findById(annuaireId)
                .orElseThrow(
                        () -> new SecretariatCustomException("Annuaire with given Id not found", NOT_FOUND));

        AnnuaireResponse annuaireResponse = new AnnuaireResponse();
        copyProperties(annuaire, annuaireResponse);

        NameResponse categorie = categorieService.getCategorieBySlug(annuaire.getCategorie_slug());
        annuaireResponse.setCategorie(categorie);

        log.info("AnnuaireServiceImpl | getAnnuaireById | annuaireResponse :" + annuaireResponse.toString());

        return annuaireResponse;
    }

    @Override
    public List<Annuaire> getAnnuaireByCategorie(String categorie) {
        log.info("AnnuaireServiceImpl | getAnnuaireByCategorie is called");
        List<Annuaire> annuaireList = new ArrayList<>();
        if (categorie.toLowerCase().equals("pisjo")) {
            String sqlquery = "SELECT e.nom as nom, e.prenoms as prenom, p.denomination as profession,\n" +
                    "s.libelle as secteur, e.tel1 as tel1, e.tel2 as tel2, e.email as email, \n" +
                    "e.domicile as domicile, e.bureau as bureau, e.bip as bip, e.no_poste as no_poste \n" +
                    "FROM employe e \n" +
                    "JOIN profession p on p.id = e.profession_id \n" +
                    "JOIN secteur s on s.id = e.secteur_id ";

            List<AnnuaireResponse> responses = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new AnnuaireResponse(
                    0,
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("profession"),
                    resultSet.getString("secteur"),
                    resultSet.getString("bureau"),
                    resultSet.getString("tel1"),
                    resultSet.getString("tel2"),
                    resultSet.getString("domicile"),
                    resultSet.getString("email"),
                    resultSet.getString("bip"),
                    resultSet.getString("no_poste"),
                    "pisjo"
            ));
            for (AnnuaireResponse employe : responses) {
                Annuaire annuaire = Annuaire.builder().build();
                copyProperties(employe, annuaire);
                annuaireList.add(annuaire);
            }
        }
        List<Annuaire> annuaires = annuaireRepository.findByCategorie(categorie);
        if (annuaires != null && annuaires.size() > 0) {
            for (Annuaire ann : annuaires) {
                NameResponse cat = categorieService.getCategorieBySlug(ann.getCategorie_slug());
                ann.setCategorie(cat);
                annuaireList.add(ann);
            }
        }
        return annuaireList;
    }

    @Override
    public void editAnnuaire(AnnuaireRequest annuaireRequest, long annuaireId) {
        log.info("AnnuaireServiceImpl | editAnnuaire is called");

        Annuaire annuaire
                = annuaireRepository.findById(annuaireId)
                .orElseThrow(() -> new SecretariatCustomException(
                        "Annuaire with given Id not found",
                        NOT_FOUND
                ));
        String categorie = categorieService.addCategorieGetSlug(annuaireRequest.getCategorie());

        annuaire.setNom(annuaireRequest.getNom());
        annuaire.setPrenom(annuaireRequest.getPrenom());
        annuaire.setProfession(annuaireRequest.getProfession());
        annuaire.setSecteur(annuaireRequest.getSecteur());
        annuaire.setBureau(annuaireRequest.getBureau());
        annuaire.setTel1(annuaireRequest.getTel1());
        annuaire.setTel2(annuaireRequest.getTel2());
        annuaire.setDomicile(annuaireRequest.getDomicile());
        annuaire.setEmail(annuaireRequest.getEmail());
        annuaire.setBip(annuaireRequest.getBip());
        annuaire.setNo_poste(annuaireRequest.getNo_poste());
        annuaire.setCategorie_slug(categorie);
        annuaireRepository.save(annuaire);

        log.info("AnnuaireServiceImpl | editAnnuaire | Annuaire Updated");
        log.info("AnnuaireServiceImpl | editAnnuaire | Annuaire Id : " + annuaire.getId());
    }

    @Override
    public void deleteAnnuaireById(long annuaireId) {
        log.info("Annuaire id: {}", annuaireId);

        if (!annuaireRepository.existsById(annuaireId)) {
            log.info("Im in this loop {}", !annuaireRepository.existsById(annuaireId));
            throw new SecretariatCustomException(
                    "Annuaire with given with Id: " + annuaireId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Annuaire with id: {}", annuaireId);
        annuaireRepository.deleteById(annuaireId);
    }

    @Override
    public List<Annuaire> getAnnuaireByCategorieAndQueryString(String categorie, String searchString) {
        log.info("AnnuaireServiceImpl | getAnnuaireByCategorieAndQueryString is called");
        List<Annuaire> annuaireList = new ArrayList<>();
        if (categorie.toLowerCase().equals("pisjo")) {
            String sqlquery = "SELECT e.nom as nom, e.prenoms as prenom, p.denomination as profession,\n" +
                    "s.libelle as secteur, e.tel1 as tel1, e.tel2 as tel2, e.email as email, \n" +
                    "e.domicile as domicile, e.bureau as bureau, e.bip as bip, e.no_poste as no_poste \n" +
                    "FROM employe e \n" +
                    "JOIN profession p on p.id = e.profession_id \n" +
                    "JOIN secteur s on s.id = e.secteur_id \n" +
                    "where concat(e.nom, e.prenoms, p.denomination, e.email) like ('%" + searchString + "%')";

            List<AnnuaireResponse> responses = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new AnnuaireResponse(
                    0,
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("profession"),
                    resultSet.getString("secteur"),
                    resultSet.getString("bureau"),
                    resultSet.getString("tel1"),
                    resultSet.getString("tel2"),
                    resultSet.getString("domicile"),
                    resultSet.getString("email"),
                    resultSet.getString("bip"),
                    resultSet.getString("no_poste"),
                    "pisjo"
            ));
            for (AnnuaireResponse employe : responses) {
                Annuaire annuaire = Annuaire.builder().build();
                copyProperties(employe, annuaire);
                annuaireList.add(annuaire);
            }
        } else {
            List<Annuaire> annuaires = annuaireRepository.findByCategorieAndQueryString(categorie, searchString);
            if (annuaires != null && annuaires.size() > 0) {
                for (Annuaire ann : annuaires) {
                    NameResponse cat = categorieService.getCategorieBySlug(ann.getCategorie_slug());
                    ann.setCategorie(cat);
                    annuaireList.add(ann);
                }
            }
        }
        return annuaireList;
    }

    @Override
    public List<Annuaire> getAnnuaireByQueryString(String searchString) {
        log.info("AnnuaireServiceImpl | getAnnuaireByQueryString is called");


        List<Annuaire> annuaireList = new ArrayList<>();
        String sqlquery = "SELECT e.nom as nom, e.prenoms as prenom, p.denomination as profession,\n" +
                "s.libelle as secteur, e.tel1 as tel1, e.tel2 as tel2, e.email as email, \n" +
                "e.domicile as domicile, e.bureau as bureau, e.bip as bip \n" +
                "FROM employe e \n" +
                "JOIN profession p on p.id = e.profession_id \n" +
                "JOIN secteur s on s.id = e.secteur_id \n" +
                "where concat(e.nom, e.prenoms, p.denomination, e.email) like ('%" + searchString + "%')";

        List<AnnuaireResponse> responses = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> new AnnuaireResponse(
                0,
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("profession"),
                resultSet.getString("secteur"),
                resultSet.getString("bureau"),
                resultSet.getString("tel1"),
                resultSet.getString("tel2"),
                resultSet.getString("domicile"),
                resultSet.getString("email"),
                resultSet.getString("bip"),
                resultSet.getString("no_poste"),
                "pisjo"
        ));
        String sqlquery0 = "SELECT e.nom as nom, e.prenoms as prenom, p.denomination as profession,\n" +
                "s.libelle as secteur, e.tel1 as tel1, e.tel2 as tel2, e.email as email, \n" +
                "e.domicile as domicile, e.bureau as bureau, e.bip as bip, e.no_poste as no_poste \n" +
                "FROM employe e \n" +
                "JOIN profession p on p.id = e.profession_id \n" +
                "JOIN secteur s on s.id = e.secteur_id \n"+
                "where concat(e.nom, e.prenoms, p.denomination, e.email) like ('%"+ searchString +"%')";

        List<AnnuaireResponse> responses0 = jdbcTemplate.query(sqlquery0, (resultSet, rowNum) -> new AnnuaireResponse(
                0,
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("profession"),
                resultSet.getString("secteur"),
                resultSet.getString("bureau"),
                resultSet.getString("tel1"),
                resultSet.getString("tel2"),
                resultSet.getString("domicile"),
                resultSet.getString("email"),
                resultSet.getString("bip"),
                resultSet.getString("no_poste"),
                "pisjo"
        ));
        for (AnnuaireResponse employe : responses0) {
            Annuaire annuaire = Annuaire.builder().build();
            copyProperties(employe, annuaire);
            annuaireList.add(annuaire);
        }

        for (AnnuaireResponse employe : responses) {
            Annuaire annuaire = Annuaire.builder().build();
            copyProperties(employe, annuaire);
            annuaireList.add(annuaire);
        }

        List<Annuaire> annuaires = annuaireRepository.findByQueryString(searchString);
        if (annuaires != null && annuaires.size() > 0) {
            for (Annuaire ann : annuaires) {
                NameResponse cat = categorieService.getCategorieBySlug(ann.getCategorie_slug());
                ann.setCategorie(cat);
                annuaireList.add(ann);
            }
        }

        return annuaireList;
    }

    @Override
    public Map<String, List<AnnuaireResponse>> getAnnuaireBips() {
        log.info("AnnuaireServiceImpl | getAnnuaireBips is called");
        String sqlQuery = "select s.libelle as secteur, e.nom as nom, e.prenoms as prenom, \n" +
                "e.no_poste as poste, e.bip as bip, e.bureau as contact \n" +
                "from employe e \n" +
                "JOIN secteur s ON e.secteur_id = s.id";

        List<AnnuaireResponse> responses = jdbcTemplate.query(sqlQuery, (resultSet, rowNum) -> new AnnuaireResponse(
                0,
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                resultSet.getString("secteur"),
                resultSet.getString("contact"),
                resultSet.getString("bip"),
                resultSet.getString("poste")
        ));

        Map<String, List<AnnuaireResponse>> secteurEmployes = new HashMap<>();
        for (AnnuaireResponse employe : responses) {
            String secteur = employe.getSecteur();
            if (!secteurEmployes.containsKey(secteur)) {
                secteurEmployes.put(secteur, new ArrayList<>());
            }
            secteurEmployes.get(secteur).add(employe);
        }
        return secteurEmployes;
    }
}
