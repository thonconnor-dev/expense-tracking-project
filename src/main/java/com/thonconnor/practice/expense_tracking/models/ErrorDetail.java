package com.thonconnor.practice.expense_tracking.models;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorDetail {
    NONE(null, HttpStatus.NOT_ACCEPTABLE);

    private Error error;
    private HttpStatus httpStatus;

    private ErrorDetail(Error error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
