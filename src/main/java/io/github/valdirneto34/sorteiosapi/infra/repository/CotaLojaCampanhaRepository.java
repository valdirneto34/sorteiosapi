package io.github.valdirneto34.sorteiosapi.infra.repository;

import io.github.valdirneto34.sorteiosapi.domain.entity.CotaLojaCampanha;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CotaLojaCampanhaRepository extends JpaRepository<CotaLojaCampanha, UUID> {
    Optional<CotaLojaCampanha> findByLojaIdAndCampanhaId(UUID lojaId, UUID campanhaId);
}