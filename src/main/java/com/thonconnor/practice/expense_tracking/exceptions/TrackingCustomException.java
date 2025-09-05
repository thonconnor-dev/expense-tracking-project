package com.thonconnor.practice.expense_tracking.exceptions;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import com.thonconnor.practice.expense_tracking.models.Error;
import com.thonconnor.practice.expense_tracking.models.ErrorDetail;

@Getter
public class TrackingCustomException extends RuntimeException {
    private Error error;
    private HttpStatus httpStatus;

    public TrackingCustomException(ErrorDetail errorDetail) {
        this(errorDetail.getError(), errorDetail.getHttpStatus());
    }

    private TrackingCustomException(Error error, HttpStatus httpStatus) {
        super(error.message());
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
