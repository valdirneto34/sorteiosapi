package io.github.valdirneto34.sorteiosapi.application.estatistica;

import io.github.valdirneto34.sorteiosapi.domain.service.SorteioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sorteios")
@RequiredArgsConstructor
public class SorteioController {

    private final SorteioService sorteioService;

    @Operation(summary = "Realiza o sorteio", description = "Seleciona aleatoriamente um cupom vencedor garantindo a proporcionalidade de chances (mais cupons = mais chances).")
    @PostMapping("/campanhas/{campanhaId}")
    public ResponseEntity<List<SorteioResponseDTO>> sortear(
            @PathVariable String campanhaId,
            @RequestParam(value = "quantidade", defaultValue = "1")  Integer quantidade) {
        List<SorteioResponseDTO> vencedores = sorteioService.realizarSorteio(campanhaId, quantidade);
        return ResponseEntity.ok(vencedores);
    }
}