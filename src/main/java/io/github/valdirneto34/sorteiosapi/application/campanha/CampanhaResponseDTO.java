package io.github.valdirneto34.sorteiosapi.application.campanha;

import io.github.valdirneto34.sorteiosapi.domain.enums.StatusCampanha;

import java.time.LocalDate;
import java.util.UUID;

public record CampanhaResponseDTO (
   UUID id,
   String nome,
   LocalDate dataInicio,
   LocalDate dataFim,
   StatusCampanha status
) {}
