package io.github.valdirneto34.sorteiosapi.domain.service;

import io.github.valdirneto34.sorteiosapi.application.cliente.ClienteMapper;
import io.github.valdirneto34.sorteiosapi.application.cupom.CupomRequestDTO;
import io.github.valdirneto34.sorteiosapi.domain.entity.*;
import io.github.valdirneto34.sorteiosapi.infra.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CupomService {

    private final CupomRepository cupomRepository;
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;
    private final CampanhaService campanhaService;
    private final CotaLojaCampanhaService cotaService;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public List<Cupom> gerarCupom(CupomRequestDTO dto) {
        Campanha campanha = campanhaService.buscarPorId(dto.campanhaId());
        LocalDate hoje = LocalDate.now();

        if (hoje.isBefore(campanha.getDataInicio()) || hoje.isAfter(campanha.getDataFim())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sorteio encerrado ou ainda não iniciado.");
        }

        Funcionario funcionario = funcionarioService.buscarPorId(dto.funcionarioId());

        if (!passwordEncoder.matches(dto.pin(), funcionario.getPin())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "PIN do funcionário incorreto ou inválido.");
        }

        Cliente cliente = clienteService.salvarOuAtualizar(clienteMapper.dtoToEntity(dto.cliente()));
        Loja loja = funcionario.getLoja();

        cotaService.registrarUsoDeCupom(loja.getId().toString(), dto.campanhaId(), dto.quantidade());

        List<Cupom> cuponsGerados = new ArrayList<>();

        for (int i = 0; i < dto.quantidade(); i++) {
            String hash = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Cupom cupom = Cupom.builder()
                    .codigo("CDL-" + hash)
                    .cliente(cliente)
                    .loja(loja)
                    .funcionario(funcionario)
                    .campanha(campanhaService.buscarPorId(dto.campanhaId()))
                    .build();
        }
        return cupomRepository.saveAll(cuponsGerados);
    }
}