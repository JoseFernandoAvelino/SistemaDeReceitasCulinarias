package projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import factorymethod.ReceitaFactory;
import singleton.GerenciadorArquivo;

public class ReceitaRepository {
    private List<Receita> receitasEmMemoria;
    private GerenciadorArquivo gerenciadorArquivo;
    private ReceitaFactory factory;

    public ReceitaRepository(ReceitaFactory factory) {
        this.factory = factory;
        this.gerenciadorArquivo = GerenciadorArquivo.getInstancia();
        this.receitasEmMemoria = new ArrayList<>();
        this.carregarDoArquivo();
    }

    public void adicionar(Receita receita) {
        this.receitasEmMemoria.add(receita);
        this.persistirAlteracoes();
    }

    public void remover(Receita receita) {
        this.receitasEmMemoria.remove(receita);
        this.persistirAlteracoes();
    }

    public void atualizar(Receita receita) {
        this.persistirAlteracoes();
    }

    public List<Receita> getTodas() {
        return new ArrayList<>(receitasEmMemoria);
    }

    public List<Receita> getFavoritas() {
        return receitasEmMemoria.stream()
                .filter(Receita::ehFavorita)
                .collect(Collectors.toList());
    }
    
    public Receita buscarPorNome(String nome) {
        for (Receita r : receitasEmMemoria) {
            if (r.getNome().equalsIgnoreCase(nome)) {
                return r;
            }
        }
        return null;
    }

    private void persistirAlteracoes() {
        List<String> linhas = new ArrayList<>();
        for (Receita r : receitasEmMemoria) {
            linhas.add(r.paraFormatoArquivo());
        }
        gerenciadorArquivo.salvarLinhas(linhas);
    }

    private void carregarDoArquivo() {
        List<String> linhas = gerenciadorArquivo.carregarLinhas();
        for (String linha : linhas) {
            try {
                String[] partes = linha.split(";");
                String tipo = partes[0];
                String nome = partes[1];
                boolean favorita = Boolean.parseBoolean(partes[2]);
                List<String> ingredientes = List.of(partes[3].split(","));
                String modoPreparo = (partes.length > 4) ? partes[4] : "Modo de preparo não informado";
                Receita r = factory.criarReceita(tipo, nome, ingredientes, modoPreparo);
                if (favorita) r.favoritar(); 
                this.receitasEmMemoria.add(r);
            } catch (Exception e) {
                System.err.println("Erro ao parsear linha: " + linha);
            }
        }
    }
}
