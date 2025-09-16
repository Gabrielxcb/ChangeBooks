package com.edu.iff.ccc.books_trade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivroNaoEncontrado extends RuntimeException {
    public LivroNaoEncontrado(String message) {
        super(message);
    }
}