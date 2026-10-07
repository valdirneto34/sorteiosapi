package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.application.estatistica.SorteioResponseDTO;
import io.github.valdirneto34.sorteiosapi.domain.entity.Cupom;
import io.github.valdirneto34.sorteiosapi.infra.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SorteioService {

    private final CupomRepository cupomRepository;
    private final SecureRandom random = new SecureRandom();

    public List<SorteioResponseDTO> realizarSorteio(String campanhaId, int quantidadeDeGanhadores){
        UUID id = UUID.fromString(campanhaId);

        long totalCupons = cupomRepository.countByCampanhaId(id);
        if(totalCupons == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há cupons registrados para esta campanha.");
        }

        int limiteSorteio = (int) Math.min(quantidadeDeGanhadores, totalCupons);

        Set<Integer> indicesSorteados = new HashSet<>();
        while (indicesSorteados.size() < limiteSorteio) {
            indicesSorteados.add(random.nextInt((int) totalCupons));
        }

        List<SorteioResponseDTO> ganhadores = new ArrayList<>();

        for (int index : indicesSorteados) {
            Page<Cupom> paginaCupom = cupomRepository.findByCampanhaId(id, PageRequest.of(index, 1));
            Cupom vencedor = paginaCupom.getContent().get(0);

            ganhadores.add(new SorteioResponseDTO(
                    vencedor.getCodigo(),
                    vencedor.getCliente().getNome(),
                    mascararCpf(vencedor.getCliente().getCpf()),
                    vencedor.getLoja().getRazaoSocial(),
                    vencedor.getFuncionario().getNome()
            ));
        }
        return ganhadores;
    }

    private String mascararCpf(String cpf) {
        return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }
}
