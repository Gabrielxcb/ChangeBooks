package com.edu.iff.ccc.books_trade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

    // --- HANDLER PARA RECURSOS NÃO ENCONTRADOS (404) ---
    @ExceptionHandler({
        UsuarioNaoEncontradoException.class,
        LivroNaoEncontradoException.class,
        PropostaNaoEncontradaException.class,
        EmailNaoEncontradoException.class
    })
    public ProblemDetail handleNotFoundException(HttpServletRequest req, RuntimeException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        pd.setTitle("Recurso Não Encontrado");
        pd.setProperty("status", HttpStatus.NOT_FOUND.value());
        pd.setProperty("message", ex.getMessage());
        pd.setProperty("exception", ex.getClass().getName());
        pd.setProperty("timestamp", LocalDateTime.now(ZoneId.systemDefault()).toString());
        pd.setProperty("path", req.getRequestURI());
        pd.setInstance(URI.create(req.getRequestURI()));

        return pd;
    }

    // --- HANDLER PARA E-MAIL JÁ CADASTRADO (400) ---
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ProblemDetail handleEmailJaCadastradoException(HttpServletRequest req, EmailJaCadastradoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());

        pd.setTitle("E-mail Já Cadastrado");
        pd.setProperty("status", HttpStatus.BAD_REQUEST.value());
        pd.setProperty("message", ex.getMessage());
        pd.setProperty("exception", ex.getClass().getName());
        pd.setProperty("timestamp", LocalDateTime.now(ZoneId.systemDefault()).toString());
        pd.setProperty("path", req.getRequestURI());
        pd.setInstance(URI.create(req.getRequestURI()));

        return pd;
    }

    // --- HANDLER PARA VALIDAÇÃO DE DTOS (@Valid) (400) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos estão inválidos.");

        pd.setTitle("Erro de Validação de Dados");
        pd.setProperty("status", HttpStatus.BAD_REQUEST.value());
        pd.setProperty("erros_de_campo", errors);
        pd.setProperty("timestamp", LocalDateTime.now(ZoneId.systemDefault()).toString());
        pd.setProperty("path", req.getRequestURI());
        pd.setInstance(URI.create(req.getRequestURI()));

        return pd;
    }
}

