package io.github.valdirneto34.sorteiosapi.application.campanha;

import io.github.valdirneto34.sorteiosapi.domain.enums.StatusCampanha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CampanhaRequestDTO(
        @NotBlank String nome,
        @NotBlank LocalDate dataInicio,
        @NotNull LocalDate dataFim,
        @NotNull StatusCampanha status
        ) {
}
