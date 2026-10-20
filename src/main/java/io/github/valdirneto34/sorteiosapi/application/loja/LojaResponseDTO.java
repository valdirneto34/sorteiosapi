package io.github.valdirneto34.sorteiosapi.application.loja;

import java.util.UUID;

public record LojaResponseDTO(
        UUID id,
        String razaoSocial,
        String cnpj,
        String telefone,
        String email,
        String logoUrl
) {}