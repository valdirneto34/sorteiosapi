package io.github.valdirneto34.sorteiosapi.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "cotas_lojas_campanhas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CotaLojaCampanha {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loja_id", nullable = false)
    private Loja loja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campanha_id", nullable = false)
    private Campanha campanha;

    @Column(name = "cota_maxima", nullable = false)
    private Integer cotaMaxima;

    @Column(name = "cupons_gerados", nullable = false)
    @Builder.Default
    private Integer cuponsGerados = 0;
}