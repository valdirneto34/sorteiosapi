package io.github.valdirneto34.sorteiosapi.infra.specs;

import io.github.valdirneto34.sorteiosapi.domain.entity.Funcionario;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpecs {

    private FuncionarioSpecs() {}

    public static Specification<Funcionario> nomeLike(String query) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("nome")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Funcionario> emailLike(String query) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("email")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Funcionario> cpfLike(String query) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("cpf")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Funcionario> isAtivo(Boolean status) {
        if (status == null) {
            return GenericSpecs.conjunction();
        }
        return (r, q, cb) -> cb.equal(r.get("ativo"), status);
    }

    public static Specification<Funcionario> omniboxSearch(String query, Boolean status) {
        Specification<Funcionario> statusSpec = isAtivo(status);

        if (query == null || query.trim().isEmpty()) {
            return statusSpec;
        }

        Specification<Funcionario> textSpec = Specification.anyOf(nomeLike(query), emailLike(query), cpfLike(query));

        return Specification.where(statusSpec).and(textSpec);
    }
}