package io.github.valdirneto34.sorteiosapi.application.exception;

import io.github.valdirneto34.sorteiosapi.application.exception.StandardErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<StandardErrorDTO.FieldErrorDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new StandardErrorDTO.FieldErrorDTO(f.getField(), f.getDefaultMessage()))
                .toList();

        StandardErrorDTO response = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardErrorDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {

        StandardErrorDTO response = new StandardErrorDTO(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de dados: O recurso já está em uso ou viola uma restrição de unicidade do sistema.",
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardErrorDTO> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {

        StandardErrorDTO response = new StandardErrorDTO(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getReason(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}