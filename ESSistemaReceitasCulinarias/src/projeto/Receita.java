package projeto;

import java.util.List;

import strategy.TipoReceitaStrategy;

public class Receita {
    private String nome;
    private List<String> ingredientes;
    private String modoDePreparo;
    private boolean favorita;
    private TipoReceitaStrategy tipoStrategy;

    public Receita(String nome, List<String> ingredientes, String modoDePreparo, TipoReceitaStrategy tipoStrategy) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.modoDePreparo = modoDePreparo;
        this.tipoStrategy = tipoStrategy;
        this.favorita = false;
    }

    public String getCategoriaDaReceita() {
        return tipoStrategy.getCategoria();
    }

    public void favoritar() {
        this.favorita = true;
    }

    public void desfavoritar() {
        this.favorita = false;
    }

    public boolean ehFavorita() {
        return favorita;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    public String getModoDePreparo() {
        return this.modoDePreparo;
    }

    public void setModoDePreparo(String modoDePreparo) {
        this.modoDePreparo = modoDePreparo;
    }
    
    public String paraFormatoArquivo() {
        return getCategoriaDaReceita() + ";" +
               getNome() + ";" +
               ehFavorita() + ";" +
               String.join(",", ingredientes); 
    }
    
    @Override
    public String toString() {
        return "Receita [" + getCategoriaDaReceita() + "] " +
               nome + " (Favorita: " + favorita + ")";
    }
}
