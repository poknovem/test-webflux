package com.springreactive.testwebflux.exception;

import com.springreactive.testwebflux.model.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(-2)
@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(CustomException.class)
    public APIResponse<String> handlerCustomException(CustomException exception){
        log.error("Exception Caught in handlerCustomException : {}", exception.getMessage());
        return APIResponse.fail(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public APIResponse<String> handlerException(Exception exception){
        log.error("Exception Caught in handlerException : {}", exception.getMessage());
        return APIResponse.fail(null);
    }
}
