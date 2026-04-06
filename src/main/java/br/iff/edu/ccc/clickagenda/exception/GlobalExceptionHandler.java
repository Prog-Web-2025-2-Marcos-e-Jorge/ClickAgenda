package br.iff.edu.ccc.clickagenda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Erro de validação");
        problemDetail.setTitle("Erro de Validação");
        problemDetail.setProperty("url", req.getRequestURL().toString());
        problemDetail.setProperty("timestamp",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("status", HttpStatus.valueOf(problemDetail.getStatus()).toString());
        problemDetail.setProperty("message", "Erro de validação");
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("path", req.getRequestURI());

        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(HttpServletRequest req, NotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso Não Encontrado");
        problemDetail.setProperty("url", req.getRequestURL().toString());
        problemDetail.setProperty("timestamp",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("status", HttpStatus.valueOf(problemDetail.getStatus()).toString());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("path", req.getRequestURI());

        return problemDetail;
    }

    @ExceptionHandler({ ForbiddenException.class, SecurityException.class })
    public ProblemDetail handleForbiddenException(HttpServletRequest req, Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setTitle("Acesso Negado");
        problemDetail.setProperty("url", req.getRequestURL().toString());
        problemDetail.setProperty("timestamp",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("status", HttpStatus.valueOf(problemDetail.getStatus()).toString());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("path", req.getRequestURI());

        return problemDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(HttpServletRequest req, BadRequestException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Requisição Inválida");
        problemDetail.setProperty("url", req.getRequestURL().toString());
        problemDetail.setProperty("timestamp",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("status", HttpStatus.valueOf(problemDetail.getStatus()).toString());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("path", req.getRequestURI());

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Argumento Inválido");
        problemDetail.setProperty("url", req.getRequestURL().toString());
        problemDetail.setProperty("timestamp",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("status", HttpStatus.valueOf(problemDetail.getStatus()).toString());
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("path", req.getRequestURI());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(HttpServletRequest req, Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno do servidor");
        problemDetail.setTitle("Erro Interno do Servidor");
        problemDetail.setProperty("url", req.getRequestURL().toString());
        problemDetail.setProperty("timestamp",
                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toString());
        problemDetail.setProperty("status", HttpStatus.valueOf(problemDetail.getStatus()).toString());
        problemDetail.setProperty("message", "Erro interno do servidor");
        problemDetail.setProperty("exception", ex.getClass().getName());
        problemDetail.setProperty("path", req.getRequestURI());

        return problemDetail;
    }
}