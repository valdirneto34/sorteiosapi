package io.github.valdirneto34.sorteiosapi.application.cupom;

import io.github.valdirneto34.sorteiosapi.application.cliente.ClienteRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CupomRequestDTO(
        @NotBlank String campanhaId,
        @NotBlank String funcionarioId,
        @NotBlank String pin,
        @NotNull @Min(1) @Max(100) Integer quantidade,
        @NotNull @Valid ClienteRequestDTO cliente
) {}