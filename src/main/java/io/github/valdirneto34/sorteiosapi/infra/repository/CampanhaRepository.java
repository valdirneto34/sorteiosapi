package io.github.valdirneto34.sorteiosapi.infra.repository;

import io.github.valdirneto34.sorteiosapi.domain.entity.Campanha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CampanhaRepository extends JpaRepository<Campanha, UUID> {
    @Query("SELECT c FROM Campanha c WHERE CURRENT_DATE BETWEEN c.dataInicio AND c.dataFim")
    Optional<Campanha> findCampanhaVigente();
}
