package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.domain.entity.Loja;
import io.github.valdirneto34.sorteiosapi.infra.repository.LojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static io.github.valdirneto34.sorteiosapi.infra.specs.LojaSpecs.omniboxSearch;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;

    @Transactional
    public Loja salvar(Loja loja) {
        if (lojaRepository.existsByCnpj(loja.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma loja cadastrada com este CNPJ.");
        }
        if (lojaRepository.existsByEmail(loja.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma loja cadastrada com este E-mail.");
        }
        return lojaRepository.save(loja);
    }

    public Loja buscarPorId(String id) {
        return lojaRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loja não encontrada."));
    }

    public Page<Loja> listarTodas(String query, Boolean status, Pageable pageable) {
        return lojaRepository.findAll(omniboxSearch(query, status), pageable);
    }

    @Transactional
    public Loja atualizar(String id, Loja lojaAtualizada) {
        Loja lojaExistente = buscarPorId(id);

        lojaExistente.setRazaoSocial(lojaAtualizada.getRazaoSocial());
        lojaExistente.setTelefone(lojaAtualizada.getTelefone());

        return lojaRepository.save(lojaExistente);
    }

    private Loja buscarIncluindoInativos(String id) {
        return lojaRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Loja não encontrada."));
    }

    @Transactional
    public void inativar(String id) {
        Loja loja = buscarIncluindoInativos(id);
        loja.setAtivo(false);
        lojaRepository.save(loja);
    }

    @Transactional
    public void reativar(String id) {
        Loja loja = buscarIncluindoInativos(id);
        loja.setAtivo(true);
        lojaRepository.save(loja);
    }

}
