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
import ro.vavedem.models.PrimarieModel;

import javax.validation.Valid;
import java.util.List;

import static ro.vavedem.restapi.constants.ApiMessageConstants.ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE;
import static ro.vavedem.restapi.constants.ApiMessageConstants.EROARE_INTERNA_INCERCATI_MAI_TARZIU;

/**
 * Controller for both public and app related info about localities
 */
@Controller
public class PrimariiAPI {

    private static final Logger logger = Logger.getLogger(PrimariiAPI.class);


    @Autowired
    private Service<PrimarieModel> primarieService;


    @ApiOperation(value = "Intoarce lista cu toate primariile.", tags = {"primarie"})
    @RequestMapping(value = {"/primarii"}, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<List<PrimarieModel>> getPrimarii() {
        logger.info("get lista primarii");

        List<PrimarieModel> primaries = null;

        try {
            primaries = primarieService.findAll();
        } catch (VaVedemApiException e) {
            if (e instanceof VaVedemConversionException) {
                logger.info(e.getMessage());

                return new ResponseEntity(ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE, HttpStatus.BAD_REQUEST);
            } else if (e instanceof VaVedemApiException) {
                logger.info(e.getMessage());

                return new ResponseEntity(EROARE_INTERNA_INCERCATI_MAI_TARZIU, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(primaries, HttpStatus.OK);
    }

    @ApiOperation(value = "Creaza o noua intrare in baza de date.", tags = {"primarie"})
    @RequestMapping(value = {"/primarii"}, method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("isFullyAuthenticated() and hasRole('ROLE_ADMIN')")
    public ResponseEntity<PrimarieModel> createPrimarie(@RequestBody @Valid PrimarieModel model) {
        logger.info("post primarie: JSON: " + model.toString());

        PrimarieModel saved = null;

        try {
            saved = primarieService.save(model);
        } catch (VaVedemApiException e) {
            if (e instanceof VaVedemConversionException) {
                logger.info(e.getMessage());

                return new ResponseEntity(ASIGURATIVA_CA_DATELE_INTRODUSE_SUNT_CORECTE, HttpStatus.BAD_REQUEST);
            } else if (e instanceof VaVedemApiException) {
                logger.info(e.getMessage());

                return new ResponseEntity(EROARE_INTERNA_INCERCATI_MAI_TARZIU, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<PrimarieModel>(saved, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Intoarce detaliile primarii cu id-ul dat.", tags = {"primarie"})
    @RequestMapping(value = {"/primarii/{id}"}, method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<PrimarieModel> getDetaliiPrimarie(@PathVariable("id") Long id) {
        logger.info("get detalii primarie: " + id);

        PrimarieModel model = null;

        try {
            model = primarieService.findOne(id);
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

        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
