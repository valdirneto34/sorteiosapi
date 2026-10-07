package io.github.valdirneto34.sorteiosapi.application.cliente;

import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String cpf,
        String telefone,
        String email,
        Boolean aceiteTermos,
        Boolean aceiteMarketing
) {}