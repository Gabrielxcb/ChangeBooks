package com.edu.iff.ccc.books_trade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String message) {
        super(message);
    }
}