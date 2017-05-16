package ro.vavedem.interfaces.database;

import ro.vavedem.exceptions.VaVedemApiException;
import ro.vavedem.exceptions.VaVedemNotSuportedException;


import java.util.List;

public interface Service<T> {

    T findOne(Long id) throws VaVedemApiException;

    List<T> findAll() throws VaVedemApiException;

    T save(T model) throws VaVedemApiException;

    void delete(T model) throws VaVedemNotSuportedException;

    List<T> findByNume(String nume) throws VaVedemNotSuportedException;
}
