package com.imanmobile.sms.domain.errors;

/**
 * Created by jome on 2014/03/08.
 */
public class MongoExceptionDTO {
    private int errorCode;
    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
