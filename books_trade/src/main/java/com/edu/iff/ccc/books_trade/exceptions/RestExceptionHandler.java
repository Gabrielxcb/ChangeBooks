package com.edu.iff.ccc.books_trade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ProblemDetail handleUsuarioNaoEncontrado(HttpServletRequest req, UsuarioNaoEncontradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Usuário Não Encontrado");
        
        problemDetail.setProperty("status", HttpStatus.NOT_FOUND.value());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setInstance(URI.create(req.getRequestURI()));
        
        return problemDetail;
    }

    @ExceptionHandler(LivroNaoEncontradoException.class)
    public ProblemDetail handleLivroNaoEncontrado(HttpServletRequest req, LivroNaoEncontradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Livro Não Encontrado");
        
        problemDetail.setProperty("status", HttpStatus.NOT_FOUND.value());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setInstance(URI.create(req.getRequestURI()));
        
        return problemDetail;
    }
    
    @ExceptionHandler(PropostaNaoEncontradaException.class)
    public ProblemDetail handlePropostaNaoEncontrada(HttpServletRequest req, PropostaNaoEncontradaException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Proposta Não Encontrada");
        
        problemDetail.setProperty("status", HttpStatus.NOT_FOUND.value());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setInstance(URI.create(req.getRequestURI()));
        
        return problemDetail;
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ProblemDetail handleEmailJaCadastrado(HttpServletRequest req, EmailJaCadastradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("E-mail Já Cadastrado");
        
        problemDetail.setProperty("status", HttpStatus.BAD_REQUEST.value());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setInstance(URI.create(req.getRequestURI()));
        
        return problemDetail;
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail handleRegraDeNegocio(HttpServletRequest req, RegraDeNegocioException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Violação de Regra de Negócio");
        
        problemDetail.setProperty("status", HttpStatus.BAD_REQUEST.value());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setInstance(URI.create(req.getRequestURI()));
        
        return problemDetail;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(HttpServletRequest req, MethodArgumentNotValidException ex) {
        
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos estão inválidos.");
        problemDetail.setTitle("Erro de Validação de Dados");
        
        problemDetail.setProperty("status", HttpStatus.BAD_REQUEST.value());
        problemDetail.setProperty("erros_de_campo", errors);
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setInstance(URI.create(req.getRequestURI()));
        
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralError(HttpServletRequest req, Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno no servidor.");
        problemDetail.setTitle("Erro Interno do Servidor");
        
        problemDetail.setProperty("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("timestamp", ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("path", req.getRequestURI());
        problemDetail.setInstance(URI.create(req.getRequestURI()));
        
        return problemDetail;
    }
}