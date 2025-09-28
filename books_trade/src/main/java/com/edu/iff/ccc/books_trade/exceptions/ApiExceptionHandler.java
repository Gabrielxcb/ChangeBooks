package com.edu.iff.ccc.books_trade.exception;

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

@ControllerAdvice // Gerenciador de exceções global
public class ApiExceptionHandler {

// --- 1. HANDLER PARA RECURSO NÃO ENCONTRADO (404) ---
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

// --- 2. HANDLER PARA VIOLAÇÃO DA REGRA DE E-MAIL (400 BAD REQUEST) ---
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

// --- 3. HANDLER PARA ERROS DE VALIDAÇÃO DE DTO (@Valid) (400) ---
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

// --- 4. HANDLER GENÉRICO (500) ---
@ExceptionHandler(Exception.class)
public ProblemDetail handleGenericException(HttpServletRequest req, Exception ex) {

    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.");

    pd.setTitle("Erro Interno");
    pd.setProperty("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    pd.setProperty("message", ex.getMessage());
    pd.setProperty("exception", ex.getClass().getName());
    pd.setProperty("timestamp", LocalDateTime.now(ZoneId.systemDefault()).toString());
    pd.setProperty("path", req.getRequestURI());

    pd.setInstance(URI.create(req.getRequestURI()));
    return pd;
}
```

}
