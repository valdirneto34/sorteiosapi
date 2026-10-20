package io.github.valdirneto34.sorteiosapi.application.cota;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CotaLojaCampanhaRequestDTO(
        @NotBlank String lojaId,
        @NotBlank String campanhaId,
        @NotNull @Min(1) Integer cotaMaxima
) {}