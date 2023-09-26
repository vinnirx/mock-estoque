package br.com.estoque.repository;

import br.com.estoque.dto.DisponibilidadeEstoque;
import br.com.estoque.fixture.EstoqueRespostaFixture;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProdutoRepository {

    public List<DisponibilidadeEstoque> findBySkus(List<Long> skus) {
        List<DisponibilidadeEstoque> estoque = EstoqueRespostaFixture.retornaListaDisponibilidadeEstoque();
        return estoque.stream()
                .filter(produto -> skus.contains(produto.getSku()))
                .collect(Collectors.toList());
    }
}

