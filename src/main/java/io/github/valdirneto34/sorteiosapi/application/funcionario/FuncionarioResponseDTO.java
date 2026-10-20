package io.github.valdirneto34.sorteiosapi.application.funcionario;

import java.util.UUID;

public record FuncionarioResponseDTO(
        UUID id,
        String nome,
        String cpf,
        String telefone,
        String email,
        UUID lojaId,
        String razaoSocialLoja
) {}