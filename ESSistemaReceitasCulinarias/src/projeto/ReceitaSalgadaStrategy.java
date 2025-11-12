package projeto;

public class ReceitaSalgadaStrategy implements TipoReceitaStrategy {
    @Override
    public String getCategoria() {
        return "Salgada";
    }
}