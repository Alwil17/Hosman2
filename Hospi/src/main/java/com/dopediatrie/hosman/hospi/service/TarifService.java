package com.dopediatrie.hosman.hospi.service;

import com.dopediatrie.hosman.hospi.payload.response.GroupeResponse;
import com.dopediatrie.hosman.hospi.payload.response.TarifGlobalResponse;
import com.dopediatrie.hosman.hospi.payload.response.TarifResponse;

import java.util.List;
import java.util.Map;

public interface TarifService {
    Map<String, List<TarifResponse>> getAllTarifs();

    TarifResponse getTarifById(long tarifId);

    TarifResponse getTarifByCode(String code);

    List<TarifResponse> getTarifForGroupeAndActe(String groupeCode, String acte);

    List<TarifResponse> getTarifForExamen();
}
