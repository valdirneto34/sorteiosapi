package io.github.valdirneto34.sorteiosapi.application.funcionario;

import io.github.valdirneto34.sorteiosapi.domain.entity.Funcionario;
import io.github.valdirneto34.sorteiosapi.domain.service.FuncionarioService;
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
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final FuncionarioMapper funcionarioMapper;

    @Operation(summary = "Cadastra um funcionário", description = "Cria um novo funcionário e vincula automaticamente a uma loja através do lojaId.")
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> criar(@Valid @RequestBody FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioMapper.dtoToEntity(dto);

        Funcionario salvo = funcionarioService.salvar(funcionario, dto.lojaId().toString());

        URI uri = buildFuncionarioURL(salvo);
        return ResponseEntity.created(uri).body(funcionarioMapper.entityToDTO(salvo));
    }

    @Operation(summary = "Busca funcionário por ID", description = "Retorna os detalhes de um funcionário ativo específico.")
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable String id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(funcionarioMapper.entityToDTO(funcionario));
    }

    @Operation(summary = "Lista funcionários de uma loja", description = "Busca funcionários com paginação. Suporta filtros dinâmicos por texto (nome/email/cpf) e status (ativo/inativo).")
    @GetMapping
    public ResponseEntity<Page<FuncionarioResponseDTO>> buscarTodos(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "status", required = false) Boolean status,
            @PageableDefault(sort = "nome") Pageable pageable) {
        Page<Funcionario> funcionarios = funcionarioService.buscarTodos(query, status, pageable);
        return ResponseEntity.ok(funcionarios.map(funcionarioMapper::entityToDTO));
    }

    @Operation(summary = "Atualiza funcionário", description = "Modifica os dados de contato (nome, telefone, email) de um funcionário.")
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizar(
            @PathVariable String id,
            @Valid @RequestBody FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioMapper.dtoToEntity(dto);
        Funcionario atualizado = funcionarioService.atualizar(id, funcionario);
        return ResponseEntity.ok(funcionarioMapper.entityToDTO(atualizado));
    }

    @Operation(summary = "Inativa um funcionário (Soft Delete)", description = "Desativa o acesso do funcionário sem apagar o histórico de cupons vinculados a ele.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable String id) {
        funcionarioService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reativa um funcionário", description = "Restaura o status ativo de um funcionário previamente inativado.")
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativar(@PathVariable String id) {
        funcionarioService.reativar(id);
        return ResponseEntity.noContent().build();
    }

    private URI buildFuncionarioURL(Funcionario funcionario) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(funcionario.getId())
                .toUri();
    }
}
