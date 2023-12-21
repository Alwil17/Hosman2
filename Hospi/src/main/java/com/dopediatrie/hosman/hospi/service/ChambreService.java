package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.entity.Chambre;
import com.dopediatrie.hosman.hospi.payload.request.ChambreRequest;
import com.dopediatrie.hosman.hospi.payload.response.ChambreResponse;

import java.util.List;

public interface ChambreService {
    List<Chambre> getAllChambres();

    long addChambre(ChambreRequest chambreRequest);

    void addChambre(List<ChambreRequest> chambreRequests);

    ChambreResponse getChambreById(long chambreId);

    void editChambre(ChambreRequest chambreRequest, long chambreId);

    public void deleteChambreById(long chambreId);

    List<Chambre> getChambreByNom(String nom);
}
