package io.github.valdirneto34.sorteiosapi.application.loja;

import io.github.valdirneto34.sorteiosapi.domain.entity.Loja;
import org.springframework.stereotype.Component;

@Component
public class LojaMapper {

    public LojaResponseDTO entityToDTO(Loja loja) {
        return new LojaResponseDTO(
                loja.getId(),
                loja.getRazaoSocial(),
                loja.getCnpj(),
                loja.getTelefone(),
                loja.getEmail(),
                loja.getLogoUrl()
        );
    }

    public Loja dtoToEntity(LojaRequestDTO dto) {
        return Loja.builder()
                .razaoSocial(dto.razaoSocial())
                .cnpj(dto.cnpj().replaceAll("\\D", ""))
                .telefone(dto.telefone())
                .email(dto.email())
                .senha(dto.senha())
                .build();
    }

}
