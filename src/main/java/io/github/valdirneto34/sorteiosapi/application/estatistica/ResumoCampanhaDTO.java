package io.github.valdirneto34.sorteiosapi.application.estatistica;

public record ResumoCampanhaDTO(
        Long totalCuponsEmitidos,
        Long totalLojasParticipantes,
        Long totalClientesUnicos
) {}