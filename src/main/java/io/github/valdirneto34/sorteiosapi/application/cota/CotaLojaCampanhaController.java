package io.github.valdirneto34.sorteiosapi.application.cota;

import io.github.valdirneto34.sorteiosapi.domain.entity.CotaLojaCampanha;
import io.github.valdirneto34.sorteiosapi.domain.service.CotaLojaCampanhaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cotas")
@RequiredArgsConstructor
public class CotaLojaCampanhaController {

    private final CotaLojaCampanhaService cotaLojaCampanhaService;
    private final CotaLojaCampanhaMapper cotaLojaCampanhaMapper;

    @Operation(summary = "Define ou atualiza a cota de cupons", description = "Vincula uma loja a uma campanha e estabelece o teto máximo de cupons que ela pode gerar.")
    @PostMapping
    public ResponseEntity<CotaLojaCampanhaResponseDTO> definirCota(@Valid @RequestBody CotaLojaCampanhaRequestDTO dto) {
        CotaLojaCampanha cota = cotaLojaCampanhaService.definirCota(dto.lojaId(), dto.campanhaId(), dto.cotaMaxima());

        return ResponseEntity.ok(cotaLojaCampanhaMapper.entityToDTO(cota));
    }
}