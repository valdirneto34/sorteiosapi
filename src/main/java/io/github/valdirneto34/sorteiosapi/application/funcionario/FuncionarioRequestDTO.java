package io.github.valdirneto34.sorteiosapi.application.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import java.util.UUID;

public record FuncionarioRequestDTO(
        @NotBlank
        String nome,

        @NotBlank
        @CPF(message = "CPF em formato inválido.")
        String cpf,

        @NotBlank
        String telefone,

        @NotBlank
        @Email
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Formato de e-mail inválido.")
        String email,

        @NotBlank
        @Pattern(regexp = "^\\d{4,6}$", message = "O PIN deve conter apenas números, entre 4 e 6 dígitos.")
        String pin,

        @NotNull(message = "O ID da Loja é obrigatório para vincular o funcionário.")
        UUID lojaId
) {}
