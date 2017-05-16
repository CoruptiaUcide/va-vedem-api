package ro.vavedem.exceptions;

/**
 * Created by CoruptiaUcide
 */
public class VaVedemEmailException extends VaVedemApiException {


    public VaVedemEmailException(String message) {
        super(message);
    }

    public VaVedemEmailException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
