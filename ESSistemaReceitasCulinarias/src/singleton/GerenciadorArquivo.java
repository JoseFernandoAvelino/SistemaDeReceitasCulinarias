package singleton;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivo {
    private static GerenciadorArquivo instancia;
    private final String NOME_ARQUIVO = "receitas.txt";
    private GerenciadorArquivo() {
        try {
            new File(NOME_ARQUIVO).createNewFile();
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo: " + e.getMessage());
        }
    }

    public static GerenciadorArquivo getInstancia() {
        if (instancia == null) {
            synchronized (GerenciadorArquivo.class) {
                if (instancia == null) {
                    instancia = new GerenciadorArquivo();
                }
            }
        }
        return instancia;
    }

    public List<String> carregarLinhas() {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar receitas: " + e.getMessage());
        }
        return linhas;
    }

    public void salvarLinhas(List<String> linhas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar receitas: " + e.getMessage());
        }
    }
}