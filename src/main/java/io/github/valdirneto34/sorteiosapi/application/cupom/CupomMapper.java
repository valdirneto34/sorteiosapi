package io.github.valdirneto34.sorteiosapi.application.cupom;

import io.github.valdirneto34.sorteiosapi.domain.entity.Cupom;
import org.springframework.stereotype.Component;

@Component
public class CupomMapper {

    public CupomResponseDTO entityToDTO(Cupom cupom) {
        return new CupomResponseDTO(
                cupom.getId(),
                cupom.getCodigo(),
                cupom.getCliente().getNome(),
                cupom.getLoja().getRazaoSocial(),
                cupom.getDataGeracao()
        );
    }
}