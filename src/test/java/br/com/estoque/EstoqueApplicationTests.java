package br.com.estoque;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.estoque.dto.DisponibilidadeEstoque;
import br.com.estoque.dto.EstoqueResposta;
import br.com.estoque.repository.ProdutoRepository;
import br.com.estoque.service.EstoqueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EstoqueApplicationTests {

	@Mock
	private ProdutoRepository produtoRepository;

	@InjectMocks
	private EstoqueService estoqueService;

	@Test
	public void verificarDisponibilidade_comTresSkus_retornaEstoqueResposta() {
		// Configuração (setup)
		String sku = "10000,50000,65000";
		List<DisponibilidadeEstoque> mockedProdutos = Arrays.asList(
				new DisponibilidadeEstoque(10000L, true, 2),
				new DisponibilidadeEstoque(50000L, false, null),
				new DisponibilidadeEstoque(65000L, true, 10)
		);
		when(produtoRepository.findBySkus(anyList())).thenReturn(mockedProdutos);

		// Execução
		EstoqueResposta resposta = estoqueService.verificarDisponibilidade(sku);

		// Verificações
		assertNotNull(resposta);
		assertEquals(3, resposta.getDisponibilidadeEstoque().size());
		assertTrue(resposta.getDisponibilidadeEstoque().get(0).getDisponibilidade());
		assertFalse(resposta.getDisponibilidadeEstoque().get(1).getDisponibilidade());
		assertTrue(resposta.getDisponibilidadeEstoque().get(2).getDisponibilidade());
	}

	@Test
	public void verificarDisponibilidade_comMaisDeDezSkus_lancaExcecao() {
		// Configuração (setup)
		String sku = "1,2,3,4,5,6,7,8,9,10,11"; // 11 SKUs

		// Execução e Verificações
		assertThrows(IllegalArgumentException.class, () -> {
			estoqueService.verificarDisponibilidade(sku);
		});
	}
}
