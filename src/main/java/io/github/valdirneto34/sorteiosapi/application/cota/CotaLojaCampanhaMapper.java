package io.github.valdirneto34.sorteiosapi.application.cota;

import io.github.valdirneto34.sorteiosapi.domain.entity.CotaLojaCampanha;
import org.springframework.stereotype.Component;

@Component
public class CotaLojaCampanhaMapper {

    public CotaLojaCampanhaResponseDTO entityToDTO(CotaLojaCampanha cota) {
        return new CotaLojaCampanhaResponseDTO(
                cota.getId(),
                cota.getLoja().getRazaoSocial(),
                cota.getCampanha().getNome(),
                cota.getCotaMaxima(),
                cota.getCuponsGerados()
        );
    }
}