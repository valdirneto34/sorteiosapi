package io.github.valdirneto34.sorteiosapi.domain.entity;

import io.github.valdirneto34.sorteiosapi.domain.enums.StatusCampanha;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "campanhas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campanha {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCampanha status;
}
