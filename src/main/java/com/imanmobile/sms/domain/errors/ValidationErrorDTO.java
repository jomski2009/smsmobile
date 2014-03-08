package com.imanmobile.sms.domain.errors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jome on 2014/03/08.
 */
public class ValidationErrorDTO {
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public ValidationErrorDTO(){}
    public void addFieldError(String path, String message){
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldErrorDTO> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
