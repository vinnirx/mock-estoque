package br.com.estoque.service;

import br.com.estoque.dto.DisponibilidadeEstoque;
import br.com.estoque.dto.EstoqueResposta;
import br.com.estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    private static final Logger logger = LoggerFactory.getLogger(EstoqueService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    public EstoqueResposta verificarDisponibilidade(String sku) {
        logger.info("Verificando disponibilidade para SKUs: {}", sku);
        String[] skus = sku.split(",");

        if (skus.length >= 5) {
            logger.warn("Quantidade excessiva de SKUs fornecidos. Máximo permitido: 5. Fornecido: {}", skus.length);
            throw new IllegalArgumentException("Máximo de 5 SKUs permitidos");
        }

        List<Long> skuIds = Arrays.stream(skus)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<DisponibilidadeEstoque> produtos = produtoRepository.findBySkus(skuIds);

        List<DisponibilidadeEstoque> resultados = new ArrayList<>();
        List<String> skusIndisponiveis = new ArrayList<>();

        for (Long skuId : skuIds) {
            DisponibilidadeEstoque produto = produtos.stream().filter(p -> p.getSku() == skuId).findFirst().orElse(null);

            if (produto != null) {
                if (produto.getDisponibilidade()) {
                    resultados.add(new DisponibilidadeEstoque(produto.getSku(), produto.getDisponibilidade(), produto.getTempoExpedicao()));
                } else {
                    resultados.add(new DisponibilidadeEstoque(produto.getSku(), false, null));
                    skusIndisponiveis.add(String.valueOf(produto.getSku()));
                }
            } else {
                logger.warn("SKU {} não encontrado no repositório", skuId);
                // SKU não encontrado, retorne como não disponível
                resultados.add(new DisponibilidadeEstoque(skuId, false, null));
                skusIndisponiveis.add(String.valueOf(skuId));
            }
        }

        EstoqueResposta resposta = new EstoqueResposta();
        resposta.setDisponibilidadeEstoque(resultados);

        if (!skusIndisponiveis.isEmpty()) {
            String skusJoin = String.join(", ", skusIndisponiveis);
            resposta.setMensagem("Desculpe-nos, mas no momento não temos estoque do(s) SKU(s) " + skusJoin);
            logger.info("SKUs indisponíveis: {}", skusJoin);
        }

        return resposta;
    }
}
