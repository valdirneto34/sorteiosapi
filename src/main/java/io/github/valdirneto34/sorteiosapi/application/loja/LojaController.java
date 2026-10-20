package io.github.valdirneto34.sorteiosapi.application.loja;

import io.github.valdirneto34.sorteiosapi.application.funcionario.FuncionarioMapper;
import io.github.valdirneto34.sorteiosapi.application.funcionario.FuncionarioResponseDTO;
import io.github.valdirneto34.sorteiosapi.domain.entity.Loja;
import io.github.valdirneto34.sorteiosapi.domain.service.FuncionarioService;
import io.github.valdirneto34.sorteiosapi.domain.service.LojaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/lojas")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;
    private final LojaMapper lojaMapper;

    @Operation(summary = "Cadastra uma loja", description = "Registra uma nova loja afiliada no sistema.")
    @PostMapping
    public ResponseEntity<LojaResponseDTO> criar(@Valid @RequestBody LojaRequestDTO dto) {
        Loja loja = lojaMapper.dtoToEntity(dto);
        Loja salva = lojaService.salvar(loja);

        URI uri = buildLojaURL(salva);
        return ResponseEntity.created(uri).body(lojaMapper.entityToDTO(salva));
    }

    @Operation(summary = "Busca loja por ID", description = "Retorna os detalhes completos de uma loja.")
    @GetMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> buscarPorId(@PathVariable String id) {
        Loja loja = lojaService.buscarPorId(id);
        return ResponseEntity.ok(lojaMapper.entityToDTO(loja));
    }

    @Operation(summary = "Lista lojas cadastradas", description = "Busca lojas com paginação. Suporta filtros dinâmicos por texto (razão social/CNPJ) e status (ativo/inativo).")
    @GetMapping
    public ResponseEntity<Page<LojaResponseDTO>> listarTodas(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "status", required = false) Boolean status,
            @PageableDefault(sort = "razaoSocial")Pageable  pageable) {
        Page<Loja> lojas = lojaService.listarTodas(query, status, pageable);
        return ResponseEntity.ok(lojas.map(lojaMapper::entityToDTO));
    }

    @Operation(summary = "Atualiza uma loja", description = "Modifica a razão social e o telefone de uma loja existente.")
    @PutMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> atualizar(@PathVariable String id, @Valid @RequestBody LojaRequestDTO dto) {
        Loja loja = lojaMapper.dtoToEntity(dto);
        Loja atualizada = lojaService.atualizar(id, loja);
        return ResponseEntity.ok(lojaMapper.entityToDTO(atualizada));
    }

    @Operation(summary = "Inativa uma loja (Soft Delete)", description = "Altera o status da loja para inativa sem apagar o histórico de cupons do banco de dados.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable String id) {
        lojaService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reativa uma loja", description = "Restaura o status ativo de uma loja previamente inativada.")
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativar(@PathVariable String id) {
        lojaService.reativar(id);
        return ResponseEntity.noContent().build();
    }

    private URI buildLojaURL(Loja loja){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(loja.getId())
                .toUri();
    }

}
