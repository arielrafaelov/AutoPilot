package com.etech7.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        logErrorDetails(e);
        ErrorResponse errorResponse = new ErrorResponse(500, "An error has occurred: " + e.getMessage() + " exception class: " + e.getClass());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NullPointerException.class, ArrayIndexOutOfBoundsException.class, IOException.class})
    public ResponseEntity<ErrorResponse> handleSpecificExceptions(Exception e) {
        logErrorDetails(e);
        ErrorResponse errorResponse = new ErrorResponse(500, "An error has occurred: " + e.getMessage() + " exception class: " + e.getClass());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleBadRequestExceptions(Exception e) {
        logErrorDetails(e);
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "Bad Request");
        errorResponse.put("message", e.getMessage());
        return errorResponse;
    }

    
    private void logErrorDetails(Exception e) {
        log.error("Localized msg: " + e.getLocalizedMessage());
        log.error("msg: " + e.getMessage());
        log.error("cause: " + e.getCause());
        log.error("class: " + e.getClass());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorResponse {
        private int code;
        private String message;
    }
}
