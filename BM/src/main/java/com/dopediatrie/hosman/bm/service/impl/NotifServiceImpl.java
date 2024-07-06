package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Notif;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.payload.request.NotifRequest;
import com.dopediatrie.hosman.bm.payload.response.NotifResponse;
import com.dopediatrie.hosman.bm.repository.NotifRepository;
import com.dopediatrie.hosman.bm.repository.ProduitRepository;
import com.dopediatrie.hosman.bm.service.NotifService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotifServiceImpl implements NotifService {
    private final String NOT_FOUND = "NOTIF_NOT_FOUND";
    private final NotifRepository notifRepository;
    private final ProduitRepository produitRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Notif> getAllNotifs() {
        return notifRepository.findAll();
    }

    @Override
    public List<Notif> getAllNotifsByProduitId(long produit_id) {
        return notifRepository.findAllByProduitId(produit_id);
    }

    @Override
    public long addNotif(NotifRequest notifRequest) {
        log.info("NotifServiceImpl | addNotif is called");

        Notif notif
                = Notif.builder()
                .titre(notifRequest.getTitre())
                .contenu(notifRequest.getTitre())
                .produit(produitRepository.findById(notifRequest.getProduit_id()).get())
                .build();

        notif = notifRepository.save(notif);

        log.info("NotifServiceImpl | addNotif | Notif Created");
        log.info("NotifServiceImpl | addNotif | Notif Id : " + notif.getId());
        return notif.getId();
    }

    @Override
    public void addNotif(List<NotifRequest> notifRequests) {
        log.info("NotifServiceImpl | addNotif is called");

        for (NotifRequest notifRequest: notifRequests) {
            Notif notif
                    = Notif.builder()
                    .titre(notifRequest.getTitre())
                    .contenu(notifRequest.getTitre())
                    .produit(produitRepository.findById(notifRequest.getProduit_id()).get())
                    .build();
            notifRepository.save(notif);
        }

        log.info("NotifServiceImpl | addNotif | Notifs Created");
    }

    @Override
    public NotifResponse getNotifById(long notifId) {
        log.info("NotifServiceImpl | getNotifById is called");
        log.info("NotifServiceImpl | getNotifById | Get the notif for notifId: {}", notifId);

        Notif notif
                = notifRepository.findById(notifId)
                .orElseThrow(
                        () -> new BMCustomException("Notif with given Id not found", NOT_FOUND));

        NotifResponse notifResponse = new NotifResponse();

        copyProperties(notif, notifResponse);

        return notifResponse;
    }

    @Override
    public void editNotif(NotifRequest notifRequest, long notifId) {
        log.info("NotifServiceImpl | editNotif is called");

        Notif notif
                = notifRepository.findById(notifId)
                .orElseThrow(() -> new BMCustomException(
                        "Notif with given Id not found",
                        NOT_FOUND
                ));
        notif.setTitre(notifRequest.getTitre());
        notif.setContenu(notifRequest.getTitre());
        notif.setProduit(produitRepository.findById(notifRequest.getProduit_id()).get());
        notifRepository.save(notif);

        log.info("NotifServiceImpl | editNotif | Notif Updated");
        log.info("NotifServiceImpl | editNotif | Notif Id : " + notif.getId());
    }

    @Override
    public void deleteNotifById(long notifId) {
        log.info("Notif id: {}", notifId);

        if (!notifRepository.existsById(notifId)) {
            log.info("Im in this loop {}", !notifRepository.existsById(notifId));
            throw new BMCustomException(
                    "Notif with given with Id: " + notifId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Notif with id: {}", notifId);
        notifRepository.deleteById(notifId);
    }

    @Override
    public List<String> getNotifLike(String q) {
        log.info("NotifServiceImpl | getNotifLike is called");
        String sqlquery = "SELECT distinct a.titre as data FROM `notif` a where a.titre like '%"+ q +"%'";

        List<String> res = jdbcTemplate.query(sqlquery, (resultSet, rowNum) -> resultSet.getString("data"));
        return res;
    }
}
