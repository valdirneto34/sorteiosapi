package io.github.valdirneto34.sorteiosapi.infra.specs;

import io.github.valdirneto34.sorteiosapi.domain.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecs {

    private ClienteSpecs() {}

    public static Specification<Cliente> nomeLike(String query) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("nome")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Cliente> cpfLike(String query) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("cpf")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Cliente> emailLike(String query) {
        return (r, q, cb) -> cb.like(cb.lower(r.get("email")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Cliente> isAtivo(Boolean status) {
        if (status == null) {
            return GenericSpecs.conjunction();
        }
        return (r, q, cb) -> cb.equal(r.get("ativo"), status);
    }

    public static Specification<Cliente> omniboxSearch(String query, Boolean status) {
        Specification<Cliente> statusSpec = isAtivo(status);

        if (query == null || query.trim().isEmpty()) {
            return statusSpec;
        }

        Specification<Cliente> textSpec = Specification.anyOf(nomeLike(query), cpfLike(query), emailLike(query));

        return Specification.where(statusSpec).and(textSpec);
    }
}
