package io.github.valdirneto34.sorteiosapi.application.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequestDTO(
        @NotBlank
        String nome,

        @NotBlank
        @Email
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Formato de e-mail inválido.")
        String email,

        @NotBlank
        @CPF(message = "CPF em formato inválido")
        String cpf,

        @NotBlank String telefone,

        @NotNull(message = "O aceite dos termos é obrigatório")
        Boolean aceiteTermos,

        @NotNull
        Boolean aceiteMarketing
) {}