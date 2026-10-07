package io.github.valdirneto34.sorteiosapi.application.campanha;

import io.github.valdirneto34.sorteiosapi.domain.entity.Campanha;
import io.github.valdirneto34.sorteiosapi.domain.service.CampanhaService;
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
@RequestMapping("/api/campanhas")
@RequiredArgsConstructor
public class CampanhaController {

    private final CampanhaService campanhaService;
    private final CampanhaMapper  campanhaMapper;

    @Operation(summary = "Cria uma nova campanha", description = "Registra uma nova campanha definindo seu período de vigência.")
    @PostMapping
    public ResponseEntity<CampanhaResponseDTO> criarCampanha(@Valid @RequestBody CampanhaRequestDTO dto) {
        Campanha campanha = campanhaMapper.dtoToEntity(dto);
        Campanha salva = campanhaService.criar(campanha);

        URI uri = buildCampanhaURL(salva);
        return ResponseEntity.created(uri).body(campanhaMapper.entityToDTO(salva));
    }

    @Operation(summary = "Busca a campanha vigente", description = "Retorna a campanha ativa na data de hoje para o frontend vincular automaticamente ao cupom.")
    @GetMapping("/vigente")
    public ResponseEntity<CampanhaResponseDTO> buscarVigente() {
        Campanha campanha = campanhaService.buscarCampanhaVigente();
        return ResponseEntity.ok(campanhaMapper.entityToDTO(campanha));
    }

    @Operation(summary = "Busca campanha por ID", description = "Retorna os detalhes de uma campanha específica.")
    @GetMapping("/{id}")
    public ResponseEntity<CampanhaResponseDTO> buscarPorId(@PathVariable String id) {
        Campanha campanha = campanhaService.buscarPorId(id);
        return ResponseEntity.ok(campanhaMapper.entityToDTO(campanha));
    }

    @Operation(summary = "Lista todas as campanhas", description = "Retorna uma lista paginada de campanhas ordenadas pela data de início (mais recentes primeiro).")
    @GetMapping
    public ResponseEntity<Page<CampanhaResponseDTO>> listarTodas(
            @PageableDefault(sort = "dataInicio", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        Page<Campanha> campanhas = campanhaService.listarTodas(pageable);
        return ResponseEntity.ok(campanhas.map(campanhaMapper::entityToDTO));
    }

    @Operation(summary = "Atualiza uma campanha", description = "Modifica os dados de uma campanha existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CampanhaResponseDTO> atualizar(@PathVariable String id, @Valid @RequestBody CampanhaRequestDTO dto) {
        Campanha campanha = campanhaMapper.dtoToEntity(dto);
        Campanha atualizada = campanhaService.atualizar(id, campanha);
        return ResponseEntity.ok(campanhaMapper.entityToDTO(atualizada));
    }

    private URI buildCampanhaURL(Campanha campanha){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(campanha.getId())
                .toUri();
    }
}
