package com.edu.iff.ccc.books_trade.exceptions;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(String message) {
        super(message);
    }
}