package billsservice.enums;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    BILL_ALREADY_EXISTS("bill already exists. Name of bill:"),
    CATEGORY_NOT_FOUND("category not found. Name category: "),
    BILL_NOT_FOUND("bill with provided id is not found. Id of bill: " ),
    SUBCATEGORY_ALREADY_EXISTS("subcategory already exists. Name of subcategory:"),
    SUBCATEGORY_NOT_FOUND("subcategory with provided id is not found. Id of subcategory: " ),
    CATEGORY_ALREADY_EXISTS("category already exists. Name of category:"),
    OPERATION_NOT_FOUND("operation with provided id is not found. Id of operation: " ),
    CURRENCY_NOT_FOUND("currency with provided id is not found. Abbr of currency: " ),
    INTERNAL_SERVER_ERROR("Internal server error"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified"),
    USER_ALREADY_EXISTS ("Record already exists");


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
