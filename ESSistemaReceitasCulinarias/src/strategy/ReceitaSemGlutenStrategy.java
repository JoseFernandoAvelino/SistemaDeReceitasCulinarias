package strategy;

public class ReceitaSemGlutenStrategy implements TipoReceitaStrategy {
    @Override
    public String getCategoria() {
        return "Sem Glúten";
    }
}