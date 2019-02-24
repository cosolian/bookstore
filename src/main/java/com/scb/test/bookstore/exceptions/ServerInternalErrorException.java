package com.scb.test.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerInternalErrorException extends RuntimeException {
    public ServerInternalErrorException() {
        super("The server experienced an internal error. Try again.");
    }
}
