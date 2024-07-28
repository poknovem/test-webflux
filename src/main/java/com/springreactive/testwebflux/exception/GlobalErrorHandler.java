package com.springreactive.testwebflux.exception;

import com.springreactive.testwebflux.model.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<APIResponse<Object>> handlerCustomException(CustomException exception){
        log.error("Exception Caught in handlerCustomException : {}", exception.getMessage());
        return ResponseEntity.status(exception.getStatusCode()).body(APIResponse.fail(exception.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<APIResponse<Object>> handlerException(Exception exception){
        log.error("Exception Caught in handlerException : {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(APIResponse.fail(null));
    }
}
