package io.github.valdirneto34.sorteiosapi.application.estatistica;

import io.github.valdirneto34.sorteiosapi.domain.service.EstatisticaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estatisticas")
@RequiredArgsConstructor
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    @Operation(summary = "Resumo do Dashboard", description = "Retorna os indicadores principais de uma campanha: cupons gerados, lojas engajadas e clientes únicos.")
    @GetMapping("/campanhas/{campanhaId}/resumo")
    public ResponseEntity<ResumoCampanhaDTO> obterResumo(@PathVariable String campanhaId) {
        ResumoCampanhaDTO resumo = estatisticaService.obterResumoCampanha(campanhaId);
        return ResponseEntity.ok(resumo);
    }

    @Operation(summary = "Ranking de Lojas (Top 10)", description = "Lista as 10 lojas que mais geraram cupons em uma determinada campanha, ordenadas decrescentemente.")
    @GetMapping("/campanhas/{campanhaId}/ranking")
    public ResponseEntity<List<LojaRankingDTO>> obterRanking(@PathVariable String campanhaId) {
        List<LojaRankingDTO> ranking = estatisticaService.obterRankingLojas(campanhaId);
        return ResponseEntity.ok(ranking);
    }
}