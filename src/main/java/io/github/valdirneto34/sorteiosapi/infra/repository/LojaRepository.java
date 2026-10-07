package io.github.valdirneto34.sorteiosapi.infra.repository;

import io.github.valdirneto34.sorteiosapi.domain.entity.Loja;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface LojaRepository extends JpaRepository<Loja, UUID>, JpaSpecificationExecutor<Loja> {
    boolean existsByCnpj(String cnpj);

    boolean existsByEmail(String email);
}