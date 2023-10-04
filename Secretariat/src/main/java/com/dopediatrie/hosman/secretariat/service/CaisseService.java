package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Caisse;
import com.dopediatrie.hosman.secretariat.payload.request.CaisseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.CaisseResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface CaisseService {
    CaisseResponse getCurrentCaisse();

    Caisse addCaisse(double amount);

    CaisseResponse addAmountCaisse(double amount);

    CaisseResponse substractAmountCaisse(double amount);

    CaisseResponse getCaisseById(long classeId);

    Caisse getCaisseByLibelle(String libelle);

    List<Caisse> getCaisseByDateminAndDatexax(LocalDateTime datemin, LocalDateTime datemax);

    CaisseResponse closeCaisseById(long classeId);
}
