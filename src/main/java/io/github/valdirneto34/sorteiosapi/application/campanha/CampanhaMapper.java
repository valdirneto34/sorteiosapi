package io.github.valdirneto34.sorteiosapi.application.campanha;

import io.github.valdirneto34.sorteiosapi.domain.entity.Campanha;
import org.springframework.stereotype.Component;

@Component
public class CampanhaMapper {

    public CampanhaResponseDTO entityToDTO(Campanha campanha) {
        return new CampanhaResponseDTO(
                campanha.getId(),
                campanha.getNome(),
                campanha.getDataInicio(),
                campanha.getDataFim(),
                campanha.getStatus()
        );
    }

    public Campanha dtoToEntity(CampanhaRequestDTO dto) {
        return Campanha.builder()
                .nome(dto.nome())
                .dataInicio(dto.dataInicio())
                .dataFim(dto.dataFim())
                .status(dto.status())
                .build();
    }

}
