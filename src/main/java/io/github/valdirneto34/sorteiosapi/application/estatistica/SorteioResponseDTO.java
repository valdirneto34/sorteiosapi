package io.github.valdirneto34.sorteiosapi.application.estatistica;

public record SorteioResponseDTO(
        String codigoCupom,
        String nomeGanhador,
        String cpfMascarado,
        String razaoSocialLoja,
        String nomeFuncionario
) {}