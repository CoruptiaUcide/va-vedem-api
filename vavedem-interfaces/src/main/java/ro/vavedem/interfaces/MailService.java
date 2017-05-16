package ro.vavedem.interfaces;

import ro.vavedem.exceptions.VaVedemApiException;



public interface MailService<T> {

    void send(T model) throws VaVedemApiException;

}

