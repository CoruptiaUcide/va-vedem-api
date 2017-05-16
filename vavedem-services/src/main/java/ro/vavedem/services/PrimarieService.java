package ro.vavedem.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ro.vavedem.exceptions.VaVedemApiException;
import ro.vavedem.exceptions.VaVedemNotFoundException;
import ro.vavedem.exceptions.VaVedemNotSuportedException;
import ro.vavedem.exceptions.VaVedemPersistenceException;
import ro.vavedem.interfaces.database.Service;
import ro.vavedem.models.PrimarieModel;
import ro.vavedem.persistence.entities.Primarie;
import ro.vavedem.persistence.repository.PrimarieRepository;
import ro.vavedem.services.util.PrimarieServUtil;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class PrimarieService implements Service<PrimarieModel> {

    private static final Logger logger = Logger.getLogger(PrimarieService.class);

    @Autowired
    private PrimarieRepository repository;

    @Override
    public PrimarieModel findOne(Long id) throws VaVedemApiException {
        final Primarie entity = repository.findOne(id);

        if (null == entity) {
            throw new VaVedemNotFoundException("Not found any record with id: " + id);
        }

        return PrimarieServUtil.convertToModel(entity);
    }

    @Override
    public List<PrimarieModel> findAll() throws VaVedemApiException {
        final List<PrimarieModel> models = new ArrayList<>();
        final List<Primarie> entities = repository.findAll();

        for (Primarie e : entities) {
            models.add(PrimarieServUtil.convertToModel(e));
        }

        return models;

    }

    @Override
    public PrimarieModel save(final PrimarieModel model) throws VaVedemApiException {
        final Primarie p = PrimarieServUtil.convertToEntity(model);
        final Primarie saved = repository.save(p);

        if (null == saved) {
            throw new VaVedemPersistenceException("Fail to save the entity.");
        }

        PrimarieModel result = PrimarieServUtil.convertToModel(saved);

        return result;

    }

    @Override
    public void delete(PrimarieModel model) throws VaVedemNotSuportedException {
        throw new VaVedemNotSuportedException("not implemented");
    }

    @Override
    public List<PrimarieModel> findByNume(String nume) throws VaVedemNotSuportedException {
        throw new VaVedemNotSuportedException("not implemented");
    }
}


