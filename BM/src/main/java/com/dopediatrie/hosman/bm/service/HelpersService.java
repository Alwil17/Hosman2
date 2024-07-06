package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.Helpers;

import java.util.List;

public interface HelpersService {
    List<Helpers> getAllHelpers();

    long addHelpers(Helpers helpersRequest);

    void addHelpers(List<Helpers> helpersRequests);

    Helpers getHelpersById(long helpersId);

    void editHelpers(Helpers helpersRequest, long helpersId);

    public void deleteHelpersById(long helpersId);

    List<Helpers> getHelpersByType(String type);
}
