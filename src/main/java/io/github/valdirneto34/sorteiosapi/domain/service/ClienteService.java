package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.domain.entity.Cliente;
import io.github.valdirneto34.sorteiosapi.infra.repository.ClienteRepository;
import io.github.valdirneto34.sorteiosapi.infra.specs.ClienteSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static io.github.valdirneto34.sorteiosapi.infra.specs.ClienteSpecs.omniboxSearch;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvarOuAtualizar(Cliente cliente) {
        if (!cliente.isAceiteTermos()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O aceite dos termos de uso (LGPD) é obrigatório.");
        }

        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cliente.getCpf());

        if (clienteExistente.isPresent()) {
            Cliente existente = clienteExistente.get();

            existente.setNome(cliente.getNome());
            existente.setTelefone(cliente.getTelefone());
            existente.setAceiteMarketing(cliente.isAceiteMarketing());

            return clienteRepository.save(existente);
        }
        return clienteRepository.save(cliente);
    }

    public Page<Cliente> buscarTodos(String query, Boolean status, Pageable pageable) {
        return clienteRepository.findAll(omniboxSearch(query, status), pageable);
    }

    public Cliente buscarPorCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("\\D", "");

        return clienteRepository.findByCpf(cpfLimpo)
                .filter(Cliente::isAtivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }
}