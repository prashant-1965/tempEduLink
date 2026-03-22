package com.cts.eduLink.application.classexception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileException extends RuntimeException {
    private final HttpStatus httpStatus;
    public FileException(String message,Throwable cause, HttpStatus httpStatus) {
        super(message,cause);
        this.httpStatus = httpStatus;
    }
}
