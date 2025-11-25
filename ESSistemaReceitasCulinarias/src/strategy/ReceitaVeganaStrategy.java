package strategy;

public class ReceitaVeganaStrategy implements TipoReceitaStrategy {
    @Override
    public String getCategoria() {
        return "Vegana";
    }
}
