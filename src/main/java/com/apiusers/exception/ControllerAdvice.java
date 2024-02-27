package com.apiusers.exception;

import com.apiusers.record.response.ResponseMessageRecord;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException( MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError e : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ErrorNotFound.class)
    public ResponseMessageRecord handleNotFoundException(ErrorNotFound ex) {
        return new ResponseMessageRecord(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ErrorDuplicateKey.class)
    public ResponseMessageRecord handleDuplicateKeyException(ErrorDuplicateKey ex) {
        return new ResponseMessageRecord(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
