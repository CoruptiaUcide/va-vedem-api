package ro.vavedem.services.util;

import org.apache.log4j.Logger;
import ro.vavedem.exceptions.VaVedemConversionException;
import ro.vavedem.models.AdresaModel;
import ro.vavedem.models.PrimarieModel;
import ro.vavedem.persistence.entities.Adresa;
import ro.vavedem.persistence.entities.Primarie;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CoruptiaUcide
 *         <p/>
 *         Converts Primarie entities to its model and vice versa
 */

// TODO change this to use a mapper. Example http://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
public final class PrimarieServUtil {

    private PrimarieServUtil() {
    }

    private static final Logger logger = Logger.getLogger(PrimarieServUtil.class);

    public static List<Primarie> convertToEntities(List<PrimarieModel> models) throws VaVedemConversionException {
        return models.stream()
                .map(PrimarieServUtil::convertToEntity)
                .collect(Collectors.toList());
    }

    public static Primarie convertToEntity(PrimarieModel model) throws VaVedemConversionException {
        Primarie p = new Primarie();

        try {
            AdresaModel addrModel = model.getAdresa();
            Adresa addrEntity = new Adresa();

            addrEntity.setStrada(addrModel.getStrada());
            addrEntity.setNumar(addrModel.getNumar());
            addrEntity.setCodPostal(addrModel.getCodPostal());
            addrEntity.setLocalitate(addrModel.getLocalitatea());

            p.setAdresa(addrEntity);
            p.setNume(model.getNume());
            p.setCodFiscal(model.getCodFiscal());
            p.setEmail(model.getEmail());
            p.setTelefon(model.getTelefon());
            p.setCodSiruta(model.getCodSiruta());

        } catch (NullPointerException npe) {
            throw new VaVedemConversionException("Null field when convertToEntity.", npe);
        }

        return p;
    }

    public static List<PrimarieModel> convertToModels(List<Primarie> entities) throws VaVedemConversionException {
        return entities.stream()
                .map(PrimarieServUtil::convertToModel)
                .collect(Collectors.toList());
    }

    public static PrimarieModel convertToModel(Primarie entity) throws VaVedemConversionException {
        PrimarieModel model = new PrimarieModel();

        try {

            Adresa addrEntity = entity.getAdresa();

            AdresaModel addrModel = new AdresaModel();
//            addrModel.setId(addrEntity.getId());
//            addrModel.setStrada(addrEntity.getStrada());
//            addrModel.setNumar(addrEntity.getNumar());
//            addrModel.setCodPostal(addrEntity.getCodPostal());
//            addrModel.setLocalitatea(addrEntity.getLocalitate());

            model.setId(entity.getId());
            model.setAdresa(addrModel);
            model.setNume(entity.getNume());
            model.setCodFiscal(entity.getCodFiscal());
            model.setEmail(entity.getEmail());
            model.setTelefon(entity.getTelefon());
            model.setCodSiruta(entity.getCodSiruta());

        } catch (NullPointerException npe) {
            throw new VaVedemConversionException("Null field when convertToModel.", npe);
        }

        return model;

    }

}
