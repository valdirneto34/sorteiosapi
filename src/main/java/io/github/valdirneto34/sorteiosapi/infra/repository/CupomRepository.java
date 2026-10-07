package io.github.valdirneto34.sorteiosapi.infra.repository;

import io.github.valdirneto34.sorteiosapi.application.estatistica.LojaRankingDTO;
import io.github.valdirneto34.sorteiosapi.domain.entity.Cupom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CupomRepository extends JpaRepository<Cupom, UUID> {

    Long countByCampanhaId(UUID campanhaId);

    @Query("SELECT COUNT(DISTINCT c.loja.id) FROM Cupom c WHERE c.campanha.id = :campanhaId")
    Long countDistinctLojaByCampanhaId(@Param("campanhaId") UUID campanhaId);

    @Query("SELECT COUNT(DISTINCT c.cliente.id) FROM Cupom c WHERE c.campanha.id = :campanhaId")
    Long countDistinctClienteByCampanhaId(@Param("campanhaId") UUID campanhaId);

    @Query("SELECT new io.github.valdirneto34.sorteiosapi.application.estatistica.LojaRankingDTO(c.loja.razaoSocial, COUNT(c.id)) " +
            "FROM Cupom c WHERE c.campanha.id = :campanhaId " +
            "GROUP BY c.loja.razaoSocial " +
            "ORDER BY COUNT(c.id) DESC")
    List<LojaRankingDTO> findRankingLojasByCampanha(@Param("campanhaId") UUID campanhaId, Pageable pageable);

    Page<Cupom> findByCampanhaId(UUID campanhaId, Pageable pageable);
}