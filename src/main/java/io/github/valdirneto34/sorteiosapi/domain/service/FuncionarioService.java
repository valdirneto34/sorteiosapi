package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.domain.entity.Funcionario;
import io.github.valdirneto34.sorteiosapi.domain.entity.Loja;
import io.github.valdirneto34.sorteiosapi.infra.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static io.github.valdirneto34.sorteiosapi.infra.specs.FuncionarioSpecs.omniboxSearch;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final LojaService lojaService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Funcionario salvar(Funcionario funcionario, String lojaId) {
        if (funcionarioRepository.existsByCpf(funcionario.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado no sistema.");
        }
        Loja loja = lojaService.buscarPorId(lojaId);
        funcionario.setLoja(loja);

        String pinCriptografado = passwordEncoder.encode(funcionario.getPin());
        funcionario.setPin(pinCriptografado);

        return funcionarioRepository.save(funcionario);
    }

    public Funcionario buscarPorId(String id) {
        return funcionarioRepository.findById(UUID.fromString(id))
                .filter(Funcionario::isAtivo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado ou inativo."));
    }

    public Page<Funcionario> buscarTodos(String query, Boolean status, Pageable pageable) {
        return funcionarioRepository.findAll(omniboxSearch(query, status), pageable);
    }

    @Transactional
    public void inativar(String id) {
        Funcionario funcionario = buscarPorId(id);
        funcionario.setAtivo(false);
        funcionarioRepository.save(funcionario);
    }

    @Transactional
    public Funcionario atualizar(String id, Funcionario dadosAtualizados) {
        Funcionario existente = buscarPorId(id);
        existente.setNome(dadosAtualizados.getNome());
        existente.setTelefone(dadosAtualizados.getTelefone());
        existente.setEmail(dadosAtualizados.getEmail());

        String pinCriptografado = passwordEncoder.encode(dadosAtualizados.getPin());
        dadosAtualizados.setPin(pinCriptografado);

        return funcionarioRepository.save(existente);
    }

    private Funcionario buscarIncluindoInativos(String id) {
        return funcionarioRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado."));
    }

    @Transactional
    public void reativar(String id) {
        Funcionario funcionario = buscarIncluindoInativos(id);
        if (funcionario.isAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O funcionário já está ativo.");
        }
        funcionario.setAtivo(true);
        funcionarioRepository.save(funcionario);
    }

    public Page<Funcionario> buscarPorLoja(String lojaId, Pageable pageable) {
        return funcionarioRepository.findByLojaId(UUID.fromString(lojaId), pageable);
    }
}
