package io.github.valdirneto34.sorteiosapi.application.loja;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CNPJ;

public record LojaRequestDTO(
        @NotBlank
        String razaoSocial,

        @NotBlank
        @CNPJ
        String cnpj,

        @NotBlank
        String telefone,

        @NotBlank
        @Email
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$" , message = "Formato de e-mail inválido." )
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$",
                message = "A senha deve conter no mínimo 6 caracteres, incluindo maiúsculas, minúsculas, números e caracteres especiais."
        )
        String senha
) {}