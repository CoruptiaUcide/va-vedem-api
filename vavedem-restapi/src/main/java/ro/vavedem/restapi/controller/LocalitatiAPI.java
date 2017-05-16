package ro.vavedem.restapi.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.vavedem.exceptions.VaVedemApiException;
import ro.vavedem.exceptions.VaVedemConversionException;
import ro.vavedem.exceptions.VaVedemNotFoundException;
import ro.vavedem.interfaces.database.Service;
import ro.vavedem.models.LocalitateModel;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static ro.vavedem.restapi.constants.ApiMessageConstants.ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE;
import static ro.vavedem.restapi.constants.ApiMessageConstants.EROARE_INTERNA_INCERCATI_MAI_TARZIU;

/**
 * Controller for both public and app related info about localities
 */
@Controller
public class LocalitatiAPI {

    private static final Logger logger = Logger.getLogger(LocalitatiAPI.class);

    @Autowired
    private Service<LocalitateModel> localitateService;

    @ApiOperation(value = "Intoarce lista cu toate localitatiile.", tags = {"localitate"})
    @RequestMapping(value = {"/localitati"}, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<LocalitateModel>> getLocalitati() {
        logger.info("getting localities");
        long startTime = System.currentTimeMillis();
        long startDBCall = System.currentTimeMillis();
        long startConversion = System.currentTimeMillis();

        // retrieve and convert the entities
        List<LocalitateModel> localitatiModel = new ArrayList<>();

        try {
            localitatiModel = localitateService.findAll();
        } catch (VaVedemApiException e) {
            if (e instanceof VaVedemConversionException) {
                logger.info(e.getMessage());

                return new ResponseEntity(ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE, HttpStatus.BAD_REQUEST);
            } else if (e instanceof VaVedemApiException) {
                logger.info(e.getMessage());

                return new ResponseEntity(EROARE_INTERNA_INCERCATI_MAI_TARZIU, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        long dbCallDuration = System.currentTimeMillis() - startDBCall;
        long conversionDuration = System.currentTimeMillis() - startConversion;
        logger.info("get localities took " + (System.currentTimeMillis() - startTime)
                + " ms, dbCall: " + dbCallDuration + "ms, conversion: " + conversionDuration + " ms");

        return new ResponseEntity<>(localitatiModel, HttpStatus.OK);
    }

    @ApiOperation(value = "Creaza o noua intrare in baza de date.", tags = {"localitate"})
    @RequestMapping(value = {"/localitati"}, method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_ADMIN')")
    public ResponseEntity<LocalitateModel> createLocalitate(@RequestBody @Valid LocalitateModel model) {
        logger.info("post localitate: JSON: " + model.toString());

        LocalitateModel saved = null;

        try {
            saved = localitateService.save(model);
        } catch (VaVedemApiException e) {
            if (e instanceof VaVedemConversionException) {
                logger.info(e.getMessage());

                return new ResponseEntity(ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE, HttpStatus.BAD_REQUEST);
            } else if (e instanceof VaVedemApiException) {
                logger.info(e.getMessage());

                return new ResponseEntity(EROARE_INTERNA_INCERCATI_MAI_TARZIU, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LocalitateModel>(saved, HttpStatus.CREATED);

    }

    @ApiOperation(value = "Intoarce detaliile localitatii cu id-ul dat.", tags = {"localitate"})
    @RequestMapping(value = {"/localitati/{id}"}, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<LocalitateModel> getDetaliiLocalitate(@PathVariable Long id) {
        logger.info("get detalii localitate: " + id);
        long startCall = System.currentTimeMillis();

        // get entity from DB
        LocalitateModel model = null;

        try {
            model = localitateService.findOne(id);
        } catch (VaVedemApiException e) {
            if (e instanceof VaVedemNotFoundException) {
                logger.info(e.getMessage());

                return new ResponseEntity(ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE, HttpStatus.BAD_REQUEST);
            } else if (e instanceof VaVedemConversionException) {
                logger.info(e.getMessage());

                return new ResponseEntity(ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE, HttpStatus.BAD_REQUEST);
            } else if (e instanceof VaVedemApiException) {
                logger.info(e.getMessage());

                return new ResponseEntity(EROARE_INTERNA_INCERCATI_MAI_TARZIU, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        long duration = System.currentTimeMillis() - startCall;
        logger.info("getting entity took: " + duration + " ms");

        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
