package ro.vavedem.exceptions;

/**
 * Created by CoruptiaUcide
 */
public class VaVedemNotSuportedException extends VaVedemApiException {


    public VaVedemNotSuportedException(String message) {
        super(message);
    }

    public VaVedemNotSuportedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
