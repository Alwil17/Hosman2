package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.response.MotifResponse;

import java.util.List;

public interface MotifService {

    MotifResponse getMotifByLibelle(String libelle);

}
