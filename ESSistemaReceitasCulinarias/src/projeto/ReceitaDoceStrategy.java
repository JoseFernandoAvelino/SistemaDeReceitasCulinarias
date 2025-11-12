package projeto;

public class ReceitaDoceStrategy implements TipoReceitaStrategy {
    @Override
    public String getCategoria() {
        return "Doce";
    }
}
