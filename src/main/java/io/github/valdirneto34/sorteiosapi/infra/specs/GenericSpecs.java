package io.github.valdirneto34.sorteiosapi.infra.specs;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecs {
    private GenericSpecs() {}

    public static <T> Specification<T> conjunction(){
        return (r, q, cb) -> cb.conjunction();
    }
}