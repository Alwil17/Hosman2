package com.dopediatrie.hosman.secretariat.service;

import com.dopediatrie.hosman.secretariat.entity.Tarif;
import com.dopediatrie.hosman.secretariat.payload.request.TarifRequest;
import com.dopediatrie.hosman.secretariat.payload.response.TarifResponse;

import java.util.List;

public interface TarifService {
    List<Tarif> getAllTarifs();

    long addTarif(TarifRequest tarifRequest);

    void addTarif(List<TarifRequest> tarifRequest);

    TarifResponse getTarifById(long tarifId);

    List<Tarif> getTarifForGroupe(String groupeCode);

    List<Tarif> getTarifForGroupeId(long groupeId);

    void editTarif(TarifRequest tarifRequest, long tarifId);

    public void deleteTarifById(long tarifId);
}
