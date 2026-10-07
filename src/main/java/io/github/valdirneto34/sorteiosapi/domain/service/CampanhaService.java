package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.domain.entity.Campanha;
import io.github.valdirneto34.sorteiosapi.infra.repository.CampanhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampanhaService {

    private final CampanhaRepository campanhaRepository;

    @Transactional
    public Campanha criar(Campanha campanha){
        return campanhaRepository.save(campanha);
    }

    public Campanha buscarPorId(String id) {
        return campanhaRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Campanha não encontrada."));
    }

    public Page<Campanha> listarTodas(Pageable pageable) {
        return campanhaRepository.findAll(pageable);
    }

    @Transactional
    public Campanha atualizar(String id, Campanha campanhaAtualizada){
        Campanha campanhaExistente = buscarPorId(id);

        campanhaExistente.setNome(campanhaAtualizada.getNome());
        campanhaExistente.setDataInicio(campanhaAtualizada.getDataInicio());
        campanhaExistente.setDataFim(campanhaAtualizada.getDataFim());
        campanhaExistente.setStatus(campanhaAtualizada.getStatus());

        return campanhaRepository.save(campanhaExistente);
    }

    public Campanha buscarCampanhaVigente() {
        return campanhaRepository.findCampanhaVigente()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma campanha ativa no momento."));
    }
}
