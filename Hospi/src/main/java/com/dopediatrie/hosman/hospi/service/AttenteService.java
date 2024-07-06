package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.request.AttenteRequest;
import com.dopediatrie.hosman.hospi.payload.response.AttenteResponse;

import java.util.List;

public interface AttenteService {
    List<AttenteResponse> getAllAttentes();

    AttenteResponse getAttenteById(long attenteId);

    AttenteResponse getAttenteByNum(long attenteNum);

    List<AttenteResponse> getAttenteFor(String vue, String medecin);

}
