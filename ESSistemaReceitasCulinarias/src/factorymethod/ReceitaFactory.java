package factorymethod;

import java.util.List;
import projeto.Receita;
import strategy.ReceitaDoceStrategy;
import strategy.ReceitaSalgadaStrategy;
import strategy.TipoReceitaStrategy;

public class ReceitaFactory {
    public Receita criarReceita(String tipo, String nome, List<String> ingredientes, String modoDePreparo) {  
        TipoReceitaStrategy strategy;

        switch (tipo.toLowerCase()) {
            case "doce":
                strategy = new ReceitaDoceStrategy();
                break;
            case "salgada":
                strategy = new ReceitaSalgadaStrategy();
                break;
            default:
                throw new IllegalArgumentException("Tipo de receita desconhecido: " + tipo);
        }

        return new Receita(nome, ingredientes, modoDePreparo, strategy);
    }
}