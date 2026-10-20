package io.github.valdirneto34.sorteiosapi.infra.repository;

import io.github.valdirneto34.sorteiosapi.domain.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID>, JpaSpecificationExecutor<Funcionario> {
    boolean existsByCpf(String cpf);
    Page<Funcionario> findByLojaId(UUID lojaId, Pageable pageable);
}
