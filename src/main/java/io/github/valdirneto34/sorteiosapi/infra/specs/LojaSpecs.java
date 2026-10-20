package io.github.valdirneto34.sorteiosapi.infra.specs;

import io.github.valdirneto34.sorteiosapi.domain.entity.Loja;
import org.springframework.data.jpa.domain.Specification;

public class LojaSpecs {
    private LojaSpecs(){}

    public static Specification<Loja> razaoSocialLike(String query){
        return (r, q, cb) -> cb.like(cb.lower(r.get("razaoSocial")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Loja> cnpjLike(String query){
        return (r, q, cb) -> cb.like(cb.lower(r.get("cnpj")), "%" + query.toLowerCase() + "%");
    }

    public static Specification<Loja> isAtivo(Boolean status){
        if(status == null){
            return GenericSpecs.conjunction();
        }
        return (r, q, cb) -> cb.equal(r.get("ativo"), status);
    }

    public static Specification<Loja> omniboxSearch(String query, Boolean status){
        Specification<Loja> statusSpec = isAtivo(status);

        if(query == null || query.trim().isEmpty()){
            return statusSpec;
        }

        Specification<Loja> textSpec = Specification.anyOf(razaoSocialLike(query), cnpjLike(query));

        return Specification.where(statusSpec).and(textSpec);
    }
}