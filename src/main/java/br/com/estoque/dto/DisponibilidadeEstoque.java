package br.com.estoque.dto;

import lombok.*;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class DisponibilidadeEstoque {
    private long sku;
    @Nullable
    private Boolean disponibilidade;
    @Nullable
    private Integer tempoExpedicao;
}