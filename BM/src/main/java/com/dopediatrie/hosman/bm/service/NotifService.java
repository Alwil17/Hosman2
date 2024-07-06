package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Notif;
import com.dopediatrie.hosman.bm.payload.request.NotifRequest;
import com.dopediatrie.hosman.bm.payload.response.NotifResponse;

import java.util.List;

public interface NotifService {
    List<Notif> getAllNotifs();

    List<Notif> getAllNotifsByProduitId(long produit_id);

    long addNotif(NotifRequest notifRequest);

    void addNotif(List<NotifRequest> notifRequests);

    NotifResponse getNotifById(long notifId);

    void editNotif(NotifRequest notifRequest, long notifId);

    public void deleteNotifById(long notifId);

    List<String> getNotifLike(String q);
}
