package br.com.estoque.fixture;

import br.com.estoque.dto.DisponibilidadeEstoque;
import br.com.estoque.dto.EstoqueResposta;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EstoqueRespostaFixture {

    public static EstoqueResposta retornaEstoque() {
        return EstoqueResposta.builder()
                .disponibilidadeEstoque(new ArrayList<>())
                .tempoResposta(Double.parseDouble("13"))
                .mensagem("")
                .build();
    }

    public static DisponibilidadeEstoque retornaDisponibilidadeEstoque(long sku, boolean disponibilidade, @Nullable Integer tempoExpedicao){
        return DisponibilidadeEstoque.builder()
                .sku(sku)
                .disponibilidade(disponibilidade)
                .tempoExpedicao(tempoExpedicao)
                .build();
    }

    public static List<DisponibilidadeEstoque> retornaListaDisponibilidadeEstoque(){
        List<DisponibilidadeEstoque> estoque = new ArrayList<>();

        estoque.add(retornaDisponibilidadeEstoque(10000, true, 2));
        estoque.add(retornaDisponibilidadeEstoque(50000, false, null));
        estoque.add(retornaDisponibilidadeEstoque(65000, true, 10));

        return estoque;
    }
}