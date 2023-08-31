package com.dopediatrie.hosman.secretariat.controller;

import com.dopediatrie.hosman.secretariat.entity.Depense;
import com.dopediatrie.hosman.secretariat.payload.request.DepenseRequest;
import com.dopediatrie.hosman.secretariat.payload.response.DepenseResponse;
import com.dopediatrie.hosman.secretariat.service.DepenseService;
import com.dopediatrie.hosman.secretariat.utils.Utils;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.List;

@RestController
@RequestMapping("/depenses")
@RequiredArgsConstructor
@Log4j2
public class DepenseController {

    private final DepenseService depenseService;

    @Autowired
    SpringTemplateEngine templateEngine;

    @GetMapping
    public ResponseEntity<List<Depense>> getAllDepenses() {

        log.info("DepenseController | getAllDepenses is called");
        return new ResponseEntity<>(depenseService.getAllDepenses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addDepense(@RequestBody DepenseRequest depenseRequest) {

        log.info("DepenseController | addDepense is called");

        log.info("DepenseController | addDepense | depenseRequest : " + depenseRequest.toString());

        long depenseId = depenseService.addDepense(depenseRequest);
        return new ResponseEntity<>(depenseId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepenseResponse> getDepenseById(@PathVariable("id") long depenseId) {

        log.info("DepenseController | getDepenseById is called");

        log.info("DepenseController | getDepenseById | depenseId : " + depenseId);

        DepenseResponse depenseResponse
                = depenseService.getDepenseById(depenseId);
        return new ResponseEntity<>(depenseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<DepenseResponse> displayDepenseById(@PathVariable("id") long depenseId) throws IOException, DocumentException {

        log.info("DepenseController | displayDepenseById is called");

        log.info("DepenseController | displayDepenseById | depenseId : " + depenseId);

        DepenseResponse depenseResponse
                = depenseService.getDepenseById(depenseId);

        Context context = new Context();

        context.setVariable("reference",depenseResponse.getReference());
        context.setVariable("total",depenseResponse.getTotal());
        context.setVariable("montant_pec",depenseResponse.getMontant_pec());
        context.setVariable("majoration",depenseResponse.getMajoration());
        context.setVariable("reduction",depenseResponse.getReduction());
        context.setVariable("a_payer",depenseResponse.getA_payer());
        context.setVariable("verse",depenseResponse.getVerse());
        context.setVariable("reliquat",depenseResponse.getReliquat());


        String htmlContentToRender = templateEngine.process("invoice", context);
        String xHtml = Utils.xhtmlConvert(htmlContentToRender);


        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);

        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources","templates")
                .toUri()
                .toURL()
                .toString();

        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        OutputStream outputStream = new FileOutputStream("src//test.pdf");
        renderer.createPDF(outputStream);
        outputStream.close();
        depenseResponse.setPath(FileSystems
                .getDefault()
                .getPath("src")
                .resolve("test.pdf")
                .toUri().toURL().toString());
        //System.out.println(outputStream.);
        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", FileSystems
                .getDefault()
                .getPath("src")
                .resolve("test.pdf")
                .toUri().toURL().toString());

        Resource resource = new UrlResource(FileSystems
                .getDefault()
                .getPath("src")
                .resolve("test.pdf")
                .toUri());*/

        return new ResponseEntity<>(depenseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editDepense(@RequestBody DepenseRequest depenseRequest,
            @PathVariable("id") long depenseId
    ) {

        log.info("DepenseController | editDepense is called");

        log.info("DepenseController | editDepense | depenseId : " + depenseId);

        depenseService.editDepense(depenseRequest, depenseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteDepenseById(@PathVariable("id") long depenseId) {
        depenseService.deleteDepenseById(depenseId);
    }
}