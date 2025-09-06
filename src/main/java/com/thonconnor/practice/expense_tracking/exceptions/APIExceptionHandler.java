package com.thonconnor.practice.expense_tracking.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.thonconnor.practice.expense_tracking.models.BaseModel;
import com.thonconnor.practice.expense_tracking.models.Error;
import com.thonconnor.practice.expense_tracking.models.ResponseResult;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class APIExceptionHandler {

    @ExceptionHandler(TrackingCustomException.class)
    public ResponseEntity<ResponseResult<BaseModel>> handle(TrackingCustomException trackingCustomException) {
        log.error("TrackingCustomException handler", trackingCustomException);
        return ResponseEntity.status(trackingCustomException.getHttpStatus().value())
                .body(ResponseResult.<BaseModel>builder().errors(List.of(trackingCustomException.getError())).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<BaseModel>> handle(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("exception while validate input ", methodArgumentNotValidException);
        List<Error> errors = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors().stream().map(fieldError -> new Error(fieldError.getDefaultMessage(),
                        fieldError.getObjectName(), "400"))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(ResponseResult.<BaseModel>builder().errors(errors).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseResult<BaseModel>> handle(Exception exception) {
        log.error("exception handler ", exception);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ResponseResult.<BaseModel>builder()
                .errors(List.of(new Error(exception.getMessage(), exception.getLocalizedMessage(), "503"))).build());
    }
}
