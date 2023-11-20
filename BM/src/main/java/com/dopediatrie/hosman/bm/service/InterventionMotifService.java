package com.dopediatrie.hosman.bm.service;

import com.dopediatrie.hosman.bm.entity.InterventionMotif;
import com.dopediatrie.hosman.bm.entity.InterventionMotifPK;
import com.dopediatrie.hosman.bm.payload.request.InterventionMotifRequest;
import com.dopediatrie.hosman.bm.payload.response.InterventionMotifResponse;

import java.util.List;

public interface InterventionMotifService {
    List<InterventionMotif> getAllInterventionMotifs();

    InterventionMotifPK addInterventionMotif(InterventionMotifRequest interventionMotifRequest);

    InterventionMotifResponse getInterventionMotifById(long interventionMotifId);

    void editInterventionMotif(InterventionMotifRequest interventionMotifRequest, long interventionMotifId);

    public void deleteInterventionMotifById(long interventionMotifId);
}
