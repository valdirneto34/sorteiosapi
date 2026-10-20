package io.github.valdirneto34.sorteiosapi.application.cupom;

import io.github.valdirneto34.sorteiosapi.domain.entity.Cupom;
import io.github.valdirneto34.sorteiosapi.domain.service.CupomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cupons")
@RequiredArgsConstructor
public class CupomController {

    private final CupomService cupomService;
    private final CupomMapper cupomMapper;

    @Operation(summary = "Gera um novo cupom", description = "Valida o limite de cotas da loja, o aceite LGPD do cliente e emite um recibo transacional com código alfanumérico único.")
    @PostMapping
    public ResponseEntity<List<CupomResponseDTO>> gerarCupom(@Valid @RequestBody CupomRequestDTO dto) {
        List<Cupom> cupons = cupomService.gerarCupom(dto);
        List<CupomResponseDTO> cuponsDto = cupons.stream().map(cupomMapper::entityToDTO).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(cuponsDto);
    }
}