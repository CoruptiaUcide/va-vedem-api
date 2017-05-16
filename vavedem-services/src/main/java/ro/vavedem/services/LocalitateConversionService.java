package ro.vavedem.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ro.vavedem.exceptions.VaVedemApiException;
import ro.vavedem.exceptions.VaVedemNotFoundException;
import ro.vavedem.exceptions.VaVedemPersistenceException;
import ro.vavedem.models.LocalitateModel;
import ro.vavedem.persistence.entities.Localitate;
import ro.vavedem.persistence.service.LocalityService;
import ro.vavedem.services.util.LocalitateServUtil;

import javax.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class LocalitateConversionService {

    private static final Logger logger = Logger.getLogger(LocalitateConversionService.class);

    @Autowired
    private LocalityService localityService;

    public LocalitateModel findOne(Long id) throws VaVedemApiException {
        final Localitate entity = localityService.findOne(id);

        if (null == entity) {
            throw new VaVedemNotFoundException("Not found any record with id: " + id);
        }

        return LocalitateServUtil.convertToModel(entity);
    }

    public List<LocalitateModel> findAll() throws VaVedemApiException {
        final List<LocalitateModel> models = new ArrayList<>();
        final List<Localitate> entities = localityService.findAll();

        for (Localitate e : entities) {
            models.add(LocalitateServUtil.convertToModel(e));
        }

        return models;
    }

    public LocalitateModel save(final LocalitateModel model) throws VaVedemApiException {
        final Localitate p = LocalitateServUtil.convertToEntity(model);
        final Localitate saved = localityService.save(p);

        if (null == saved) {
            throw new VaVedemPersistenceException("Fail to save the entity.");
        }
        return LocalitateServUtil.convertToModel(saved);

    }

    public void delete(LocalitateModel model) throws NotSupportedException {
        throw new NotSupportedException("not implemented");
    }

    public List<LocalitateModel> findByNume(String nume) throws NotSupportedException {
        throw new NotSupportedException("not implemented");
    }
}


