package io.github.valdirneto34.sorteiosapi.application.cliente;

import io.github.valdirneto34.sorteiosapi.domain.entity.Cliente;
import io.github.valdirneto34.sorteiosapi.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Operation(summary = "Cria ou atualiza um cliente", description = "Registra um novo cliente com validação LGPD ou atualiza os dados de contato se o CPF já existir.")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarOuAtualizar(@Valid @RequestBody ClienteRequestDTO dto) {
        Cliente cliente = clienteMapper.dtoToEntity(dto);
        Cliente salvo = clienteService.salvarOuAtualizar(cliente);
        return ResponseEntity.ok(clienteMapper.entityToDTO(salvo));
    }

    @Operation(summary = "Busca cliente por CPF", description = "Retorna os dados do cliente caso ele já tenha cadastro ativo no sistema.")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarPorCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(clienteMapper.entityToDTO(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> buscarTodos(
            @RequestParam(value = "query", required = false, defaultValue = "") String query,
            @RequestParam(value = "status", required = false) Boolean status,
            @PageableDefault(sort = "nome") Pageable pageable) {
        Page<Cliente> clientes =  clienteService.buscarTodos(query, status, pageable);
        return ResponseEntity.ok(clientes.map(clienteMapper::entityToDTO));
    }
}