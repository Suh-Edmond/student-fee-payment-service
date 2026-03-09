package com.student_mgt_platform.fee_payment.domain.exceptions;

import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final String API_URL = "https://localhost:8000/api/"; //TODO; Change to actual prod url


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setDetail(e.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create(API_URL));
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        return problemDetail;
    }

    @ExceptionHandler(BusinessValidationException.class)
    public ProblemDetail handleBusinessValidationException(BusinessValidationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setDetail(e.getMessage());
        problemDetail.setTitle("Fee Payment Business Validation");
        problemDetail.setType(URI.create(API_URL));
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        return problemDetail;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                FieldError::getField,
                fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value",
                (existing, replacement) -> replacement
        ));
        problemDetail.setTitle("Validation Error");
        problemDetail.setType(URI.create(API_URL));
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        problemDetail.setProperty("invalid_parameters", errors);

        return new ResponseEntity<>(problemDetail, headers, status);
    }
}
