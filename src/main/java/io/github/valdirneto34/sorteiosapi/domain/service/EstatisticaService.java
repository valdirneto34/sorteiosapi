package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.application.estatistica.LojaRankingDTO;
import io.github.valdirneto34.sorteiosapi.application.estatistica.ResumoCampanhaDTO;
import io.github.valdirneto34.sorteiosapi.infra.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EstatisticaService {

    private final CupomRepository cupomRepository;
    private final CampanhaService campanhaService;

    public ResumoCampanhaDTO obterResumoCampanha(String campanhaId) {
        campanhaService.buscarPorId(campanhaId);

        UUID id = UUID.fromString(campanhaId);

        Long totalCupons = cupomRepository.countByCampanhaId(id);
        Long totalLojas = cupomRepository.countDistinctLojaByCampanhaId(id);
        Long totalClientes = cupomRepository.countDistinctClienteByCampanhaId(id);

        return new ResumoCampanhaDTO(totalCupons, totalLojas, totalClientes);
    }

    public List<LojaRankingDTO> obterRankingLojas(String campanhaId) {
        campanhaService.buscarPorId(campanhaId);

        return cupomRepository.findRankingLojasByCampanha(
                UUID.fromString(campanhaId),
                PageRequest.of(0, 10)
        );
    }
}