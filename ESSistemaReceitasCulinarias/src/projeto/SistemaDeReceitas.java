package projeto;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import factorymethod.ReceitaFactory;

public class SistemaDeReceitas {
    private final ReceitaRepository repository;
    private final ReceitaFactory factory;
    private final Scanner scanner;

    public SistemaDeReceitas(ReceitaRepository repository, ReceitaFactory factory, Scanner scanner) {
        this.repository = repository;
        this.factory = factory;
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReceitaFactory factory = new ReceitaFactory();
        ReceitaRepository repository = new ReceitaRepository(factory);
        SistemaDeReceitas sistema = new SistemaDeReceitas(repository, factory, scanner);
        sistema.iniciar();
        scanner.close();
    }

    public void iniciar() {
        System.out.println("--- Bem vindo ao Sistemas de Receitas Culinárias ---");
        boolean executando = true;
        while (executando) {
            exibirMenu();
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarReceita();
                        break;
                    case 2:
                        listarTodasReceitas();
                        break;
                    case 3:
                        editarReceita();
                        break;
                    case 4:
                        apagarReceita();
                        break;
                    case 5:
                        favoritarReceita();
                        break;
                    case 6:
                        listarReceitasFavoritas();
                        break;
                    case 7:
                        verModoDePreparo();
                        break;    
                    case 0:
                        executando = false;
                        System.out.println("Suas receitas foram salvas.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }

            } catch (InputMismatchException e) {
                System.err.println("Erro: Por favor, digite apenas números.");
                scanner.nextLine();
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Cadastrar nova receita");
        System.out.println("2. Listar todas as receitas");
        System.out.println("3. Editar uma receita");
        System.out.println("4. Apagar uma receita");
        System.out.println("5. Favoritar/Desfavoritar receita");
        System.out.println("6. Listar receitas favoritas");
        System.out.println("7. Ver modo de preparo");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarReceita() {
        try {
            System.out.println("\n--- Cadastro de Receita ---");
            System.out.print("Nome da receita: ");
            String nome = scanner.nextLine();

            System.out.print("Tipo (Doce / Salgada): ");
            String tipo = scanner.nextLine();

            System.out.print("Ingredientes (separados por vírgula): ");
            String ingrString = scanner.nextLine();
            List<String> ingredientes = List.of(ingrString.split(","));

            System.out.print("Modo de preparo: ");
            String modoPreparo = scanner.nextLine();
            Receita novaReceita = factory.criarReceita(tipo, nome, ingredientes, modoPreparo);          
            repository.adicionar(novaReceita);
            
            System.out.println("Receita '" + nome + "' cadastrada com sucesso!");

        } catch (IllegalArgumentException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
        }
    }

    private void listarTodasReceitas() {
        System.out.println("\n--- Todas as Receitas ---");
        List<Receita> receitas = repository.getTodas();
        if (receitas.isEmpty()) {
            System.out.println("Nenhuma receita cadastrada.");
            return;
        }
        for (Receita r : receitas) {
            System.out.println(r);
        }
    }

    private void editarReceita() {
        System.out.println("\n--- Editar Receita ---");
        System.out.print("Digite o nome da receita que deseja editar: ");
        String nomeBusca = scanner.nextLine();
        Receita receita = repository.buscarPorNome(nomeBusca);
        if (receita == null) {
            System.err.println("Receita não encontrada.");
            return;
        }

        System.out.println("Editando: " + receita.getNome());
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) {
            receita.setNome(nome);
        }

        System.out.print("Novos ingredientes: ");
        String ingrString = scanner.nextLine();
        if (!ingrString.isBlank()) {
            receita.setIngredientes(List.of(ingrString.split(",")));
        }

        System.out.print("Novo modo de preparo: ");
        String modoPreparo = scanner.nextLine();
        if (!modoPreparo.isBlank()) {
            receita.setModoDePreparo(modoPreparo);
        }

        repository.atualizar(receita);
        System.out.println("Receita atualizada com sucesso!");
    }

    private void apagarReceita() {
        System.out.println("\n--- Apagar Receita ---");
        System.out.print("Digite o nome da receita que deseja apagar: ");
        String nomeBusca = scanner.nextLine();
        
        Receita receita = repository.buscarPorNome(nomeBusca);
        if (receita == null) {
            System.err.println("Receita não encontrada.");
            return;
        }

        repository.remover(receita);
        System.out.println("Receita '" + receita.getNome() + "' removida.");
    }

    private void favoritarReceita() {
        System.out.println("\n--- Favoritar/Desfavoritar ---");
        System.out.print("Digite o nome da receita: ");
        String nomeBusca = scanner.nextLine();
        
        Receita receita = repository.buscarPorNome(nomeBusca);
        if (receita == null) {
            System.err.println("Receita não encontrada.");
            return;
        }

        if (receita.ehFavorita()) {
            receita.desfavoritar();
            System.out.println("Receita '" + receita.getNome() + "' desfavoritada.");
        } else {
            receita.favoritar();
            System.out.println("Receita '" + receita.getNome() + "' favoritada.");
        }
        
        repository.atualizar(receita);
    }

    private void listarReceitasFavoritas() {
        System.out.println("\n--- Receitas Favoritas ---");
        List<Receita> favoritas = repository.getFavoritas();
        if (favoritas.isEmpty()) {
            System.out.println("Nenhuma receita favorita encontrada.");
            return;
        }
        for (Receita r : favoritas) {
            System.out.println(r);
        }
    }
    
    private void verModoDePreparo() {
        System.out.println("\n--- Modo de Preparo ---");
        System.out.print("Digite o nome da receita que deseja ver: ");
        String nomeBusca = scanner.nextLine();
        Receita receita = repository.buscarPorNome(nomeBusca);
        if (receita == null) {
                System.err.println("Receita não encontrada.");
            return;
        }

        System.out.println("\n---------------------------------");
        System.out.println("Receita: " + receita.getNome());
        System.out.println("---------------------------------");
        System.out.println(receita.getModoDePreparo());
        System.out.println("---------------------------------");            
    }
}