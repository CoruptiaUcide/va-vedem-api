package ro.vavedem.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import ro.vavedem.persistence.entities.Localitate;

import java.util.List;

public interface LocalitateRepository extends CrudRepository<Localitate, Long> {

    Localitate findOne(Long id);

    List<Localitate> findAll();

    Localitate save(Localitate primarie);

}
