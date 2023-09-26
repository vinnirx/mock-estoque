package br.com.estoque.controller;

import br.com.estoque.dto.EstoqueResposta;
import br.com.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private static final Logger logger = LoggerFactory.getLogger(EstoqueController.class);

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping("disponibilidade/{sku}")
    public ResponseEntity<EstoqueResposta> checkDisponibilidade(@PathVariable String sku) {
        long startTime = System.currentTimeMillis();
        logger.info("Verificando disponibilidade para SKU: {}", sku);

        EstoqueResposta resposta = estoqueService.verificarDisponibilidade(sku);

        long endTime = System.currentTimeMillis();
        double responseTime = (endTime - startTime) / 1000.0;
        resposta.setTempoResposta(responseTime);

        logger.info("Disponibilidade para SKU {}: {}. Tempo de resposta: {} segundos", sku, resposta, responseTime);
        return ResponseEntity.ok(resposta);
    }
}
