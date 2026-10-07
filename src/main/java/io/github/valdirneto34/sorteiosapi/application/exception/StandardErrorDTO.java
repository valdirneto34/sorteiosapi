package io.github.valdirneto34.sorteiosapi.application.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StandardErrorDTO(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String path,
    List<FieldErrorDTO> fieldErrors
) {
    public record FieldErrorDTO(String field, String message) {}
}
