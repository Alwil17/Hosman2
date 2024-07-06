package com.dopediatrie.hosman.bm.service.impl;

import com.dopediatrie.hosman.bm.entity.Helpers;
import com.dopediatrie.hosman.bm.exception.BMCustomException;
import com.dopediatrie.hosman.bm.repository.HelpersRepository;
import com.dopediatrie.hosman.bm.service.HelpersService;
import com.dopediatrie.hosman.bm.utils.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class HelpersServiceImpl implements HelpersService {
    private final HelpersRepository helperRepository;
    private final String NOT_FOUND = "HELPERS_NOT_FOUND";

    @Override
    public List<Helpers> getAllHelpers() {
        return helperRepository.findAll();
    }

    @Override
    public long addHelpers(Helpers helperRequest) {
        log.info("HelpersServiceImpl | addHelpers is called");

        Helpers helper;
        if(helperRepository.existsBySlug(Str.slug(helperRequest.getContent()))){
            helper = helperRepository.findBySlug(Str.slug(helperRequest.getContent())).orElseThrow();
        }else{
            helper = Helpers.builder()
                    .content(helperRequest.getContent())
                    .slug(Str.slug(helperRequest.getContent()))
                    .type(helperRequest.getType())
                    .build();
            helper = helperRepository.save(helper);
        }

        log.info("HelpersServiceImpl | addHelpers | Helpers Created");
        log.info("HelpersServiceImpl | addHelpers | Helpers Id : " + helper.getId());
        return helper.getId();
    }

    @Override
    public void addHelpers(List<Helpers> helperRequests) {
        log.info("HelpersServiceImpl | addHelpers is called");

        for (Helpers helperRequest: helperRequests) {
            Helpers helper = Helpers.builder()
                    .content(helperRequest.getContent())
                    .slug(Str.slug(helperRequest.getContent()))
                    .type(helperRequest.getType())
                    .build();
            helperRepository.save(helper);
        }

        log.info("HelpersServiceImpl | addHelpers | Helperss Created");
    }

    @Override
    public Helpers getHelpersById(long helperId) {
        log.info("HelpersServiceImpl | getHelpersById is called");
        log.info("HelpersServiceImpl | getHelpersById | Get the helper for helperId: {}", helperId);

        Helpers helper
                = helperRepository.findById(helperId)
                .orElseThrow(
                        () -> new BMCustomException("Helpers with given Id not found", NOT_FOUND));

        return helper;
    }

    @Override
    public void editHelpers(Helpers helperRequest, long helperId) {
        log.info("HelpersServiceImpl | editHelpers is called");

        Helpers helper
                = helperRepository.findById(helperId)
                .orElseThrow(() -> new BMCustomException(
                        "Helpers with given Id not found",
                        NOT_FOUND
                ));
        helper.setContent(helperRequest.getContent());
        helper.setSlug(Str.slug(helperRequest.getSlug()));
        helper.setType(helperRequest.getType());
        helperRepository.save(helper);

        log.info("HelpersServiceImpl | editHelpers | Helpers Updated");
        log.info("HelpersServiceImpl | editHelpers | Helpers Id : " + helper.getId());
    }

    @Override
    public void deleteHelpersById(long helperId) {
        log.info("Helpers id: {}", helperId);

        if (!helperRepository.existsById(helperId)) {
            log.info("Im in this loop {}", !helperRepository.existsById(helperId));
            throw new BMCustomException(
                    "Helpers with given with Id: " + helperId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting Helpers with id: {}", helperId);
        helperRepository.deleteById(helperId);
    }

    @Override
    public List<Helpers> getHelpersByType(String type) {
        log.info("HelpersServiceImpl | getHelpersByQueryString is called");
        if(helperRepository.existsByType(type)){
            return helperRepository.findByType(type);
        }
       return new ArrayList<Helpers>();
    }
}
