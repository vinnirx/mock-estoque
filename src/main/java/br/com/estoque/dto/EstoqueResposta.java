package br.com.estoque.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EstoqueResposta {

    private List<DisponibilidadeEstoque> disponibilidadeEstoque;
    private double tempoResposta;
    private String mensagem;

    public EstoqueResposta() {
    }
}