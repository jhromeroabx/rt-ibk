package org.example.infrastructure.adapter.in.web;

import org.example.domain.exception.DuplicateEmailException;
import org.example.domain.exception.InvalidPasswordException;
import org.example.users.api.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDuplicate() {
        return Mono.just(
                ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponse("El correo ya registrado"))
        );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidation(WebExchangeBindException ex) {

        String message = ex.getFieldErrors()
                .stream()
                .findFirst()
                .map(this::mapMessage)
                .orElse("Solicitud inválida");

        return Mono.just(
                ResponseEntity
                        .badRequest()
                        .body(new ErrorResponse(message))
        );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidPassword() {
        return Mono.just(
                ResponseEntity.badRequest()
                        .body(new ErrorResponse("La contraseña no cumple el formato requerido"))
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGeneric(Exception ex) {

        ex.printStackTrace();

        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse("Error interno del servidor"))
        );
    }

    private String mapMessage(FieldError error) {
        return switch (error.getField()) {
            case "email" -> "El correo no tiene un formato válido";
            case "password" -> "La contraseña no cumple el formato requerido";
            case "name" -> "El nombre es obligatorio";
            default -> "Solicitud inválida";
        };
    }
}