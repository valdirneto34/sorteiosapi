package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.domain.entity.CotaLojaCampanha;
import io.github.valdirneto34.sorteiosapi.infra.repository.CotaLojaCampanhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CotaLojaCampanhaService {

    private final CotaLojaCampanhaRepository cotaLojaCampanhaRepository;
    private final LojaService lojaService;
    private final CampanhaService campanhaService;

    @Transactional
    public CotaLojaCampanha definirCota(String lojaId, String campanhaId, Integer cotaMaxima) {
        return cotaLojaCampanhaRepository.findByLojaIdAndCampanhaId(UUID.fromString(lojaId), UUID.fromString(campanhaId))
                .map(cotaExistente -> {
                    cotaExistente.setCotaMaxima(cotaMaxima);
                    return cotaLojaCampanhaRepository.save(cotaExistente);
                })
                .orElseGet(() -> {
                    CotaLojaCampanha novaCota = CotaLojaCampanha.builder()
                            .loja(lojaService.buscarPorId(lojaId))
                            .campanha(campanhaService.buscarPorId(campanhaId))
                            .cotaMaxima(cotaMaxima)
                            .build();
                    return cotaLojaCampanhaRepository.save(novaCota);
                });
    }
    @Transactional
    public void registrarUsoDeCupom(String lojaId, String campanhaId, Integer quantidade) {
        CotaLojaCampanha cota = cotaLojaCampanhaRepository.findByLojaIdAndCampanhaId(UUID.fromString(lojaId), UUID.fromString(campanhaId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Esta loja não possui cota ou vínculo com esta campanha."));

        if (cota.getCuponsGerados() + quantidade > cota.getCotaMaxima()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A cota de cupons desta loja é insuficiente para essa quantidade.");
        }

        cota.setCuponsGerados(cota.getCuponsGerados() + quantidade);
        cotaLojaCampanhaRepository.save(cota);
    }
}
