package currencyservice.enums;

public enum ErrorMessages {


    CURRENCY_ALREADY_EXISTS("Currency already exists. Name of currency: "),
    CURRENCY_NOT_FOUND("currency not found. ABBR of currency: ");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
