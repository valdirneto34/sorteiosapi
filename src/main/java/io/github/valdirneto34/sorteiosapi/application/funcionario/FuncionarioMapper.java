package io.github.valdirneto34.sorteiosapi.application.funcionario;

import io.github.valdirneto34.sorteiosapi.domain.entity.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public FuncionarioResponseDTO entityToDTO(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getTelefone(),
                funcionario.getEmail(),
                funcionario.getLoja().getId(),
                funcionario.getLoja().getRazaoSocial()
        );
    }

    public Funcionario dtoToEntity(FuncionarioRequestDTO dto) {
        return Funcionario.builder()
                .nome(dto.nome())
                .cpf(dto.cpf().replaceAll("\\D", ""))
                .telefone(dto.telefone())
                .email(dto.email())
                .pin(dto.pin())
                .build();
    }
}
