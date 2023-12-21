package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.payload.response.ActeResponse;

import java.util.List;

public interface ActeService {

    ActeResponse getActeByCode(String code);
}
