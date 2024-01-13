package com.dopediatrie.hosman.hospi.controller;

import com.dopediatrie.hosman.hospi.entity.Addressed;
import com.dopediatrie.hosman.hospi.payload.request.AddressedRequest;
import com.dopediatrie.hosman.hospi.payload.response.AddressedResponse;
import com.dopediatrie.hosman.hospi.service.AddressedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressed")
@RequiredArgsConstructor
@Log4j2
public class AddressedController {

    private final AddressedService addressedService;

    @GetMapping
    public ResponseEntity<List<Addressed>> getAllAddresseds() {

        log.info("AddressedController | getAllAddresseds is called");
        return new ResponseEntity<>(addressedService.getAllAddresseds(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addAddressed(@RequestBody AddressedRequest addressedRequest) {

        log.info("AddressedController | addAddressed is called");

        log.info("AddressedController | addAddressed | addressedRequest : " + addressedRequest.toString());

        long addressedId = addressedService.addAddressed(addressedRequest);
        return new ResponseEntity<>(addressedId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressedResponse> getAddressedById(@PathVariable("id") long addressedId) {

        log.info("AddressedController | getAddressedById is called");

        log.info("AddressedController | getAddressedById | addressedId : " + addressedId);

        AddressedResponse addressedResponse
                = addressedService.getAddressedById(addressedId);
        return new ResponseEntity<>(addressedResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editAddressed(@RequestBody AddressedRequest addressedRequest,
            @PathVariable("id") long addressedId) {
        log.info("AddressedController | editAddressed is called");
        log.info("AddressedController | editAddressed | addressedId : " + addressedId);

        addressedService.editAddressed(addressedRequest, addressedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteAddressedById(@PathVariable("id") long addressedId) {
        addressedService.deleteAddressedById(addressedId);
    }
}