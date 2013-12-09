package ch.friedli.infosystem.business.exception;

/**
 *
 * @author Michael Friedli
 */
public class BusinessTechnicalException extends RuntimeException {

    private Exception exeption;
    private String errorText;

    public BusinessTechnicalException(Exception e, String errorText) {
        this.exeption = exeption;
        this.errorText = errorText;
    }

    public BusinessTechnicalException(String errorText) {
        this.exeption = null;
        this.errorText = errorText;
    }

    /**
     * @return the exeption
     */
    public Exception getExeption() {
        return exeption;
    }

    /**
     * @return the errorText
     */
    public String getErrorText() {
        return errorText;
    }

}
