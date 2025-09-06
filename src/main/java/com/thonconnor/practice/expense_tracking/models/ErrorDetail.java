package com.thonconnor.practice.expense_tracking.models;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorDetail {
    CATEGORY_NOT_FOUND(new Error("no category record found", "given id is invalid", "ERR001"), HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(new Error("no user record found", "given id is invalid", "ERR002"), HttpStatus.NOT_FOUND);
    ;

    private Error error;
    private HttpStatus httpStatus;

    private ErrorDetail(Error error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
