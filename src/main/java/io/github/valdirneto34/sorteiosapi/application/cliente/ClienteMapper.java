package io.github.valdirneto34.sorteiosapi.application.cliente;

import io.github.valdirneto34.sorteiosapi.domain.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteResponseDTO entityToDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.isAceiteTermos(),
                cliente.isAceiteMarketing()
        );
    }

    public Cliente dtoToEntity(ClienteRequestDTO dto) {
        return Cliente.builder()
                .nome(dto.nome())
                .cpf(dto.cpf().replaceAll("\\D", ""))
                .telefone(dto.telefone())
                .email(dto.email())
                .aceiteTermos(dto.aceiteTermos())
                .aceiteMarketing(dto.aceiteMarketing())
                .ativo(true)
                .build();
    }
}