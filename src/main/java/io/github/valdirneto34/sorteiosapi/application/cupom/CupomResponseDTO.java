package io.github.valdirneto34.sorteiosapi.application.cupom;

import java.time.LocalDateTime;
import java.util.UUID;

public record CupomResponseDTO(
        UUID id,
        String codigo,
        String nomeCliente,
        String razaoSocialLoja,
        LocalDateTime dataGeracao
) {}