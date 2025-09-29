package com.edu.iff.ccc.books_trade.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNaoEncontradoException extends RuntimeException {
    public EmailNaoEncontradoException(String message) {
        super(message);
    }
}
