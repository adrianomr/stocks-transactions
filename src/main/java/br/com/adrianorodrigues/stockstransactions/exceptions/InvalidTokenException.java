package br.com.adrianorodrigues.stockstransactions.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST);
    }

}
