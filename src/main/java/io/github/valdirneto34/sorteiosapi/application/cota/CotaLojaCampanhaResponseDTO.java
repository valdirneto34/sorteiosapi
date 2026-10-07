package io.github.valdirneto34.sorteiosapi.application.cota;

import java.util.UUID;

public record CotaLojaCampanhaResponseDTO(
        UUID id,
        String razaoSocialLoja,
        String nomeCampanha,
        Integer cotaMaxima,
        Integer cuponsGerados
) {}