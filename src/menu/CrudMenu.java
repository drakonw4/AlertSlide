package menu;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class CrudMenu<T> {

    protected final Scanner scanner = new Scanner(System.in);

    private final String titulo;
    private final Function<Integer, T> buscador;          // service.buscarPorId(id)
    private final Consumer<T> atualizador;                // service.atualizar(obj)
    private final Consumer<T> cadastrador;                // service.cadastrar(obj)
    private final Function<Integer, Boolean> excluidor;   // service.excluir(id) -> boolean
    private final Function<Void, List<T>> listaTodos;     // service.listar()

    /**
     * Construtor de CrudMenu sem Supplier<T>.
     *
     * @param titulo       texto (ex.: "Usuário", "Sensor", etc.)
     * @param listaTodos   referência a service.listar()
     * @param buscador     referência a service.buscarPorId(int)
     * @param atualizador  referência a service.atualizar(T)
     * @param excluidor    referência a service.excluir(int)
     * @param cadastrador  referência a service.cadastrar(T)
     */
    protected CrudMenu(
            String titulo,
            Function<Void, List<T>> listaTodos,
            Function<Integer, T> buscador,
            Consumer<T> atualizador,
            Function<Integer, Boolean> excluidor,
            Consumer<T> cadastrador
    ) {
        this.titulo = titulo;
        this.listaTodos = listaTodos;
        this.buscador = buscador;
        this.atualizador = atualizador;
        this.excluidor = excluidor;
        this.cadastrador = cadastrador;
    }

    /**
     * Cada subclasse deve implementar este método, que simplesmente lê do console
     * todos os campos necessários para criar um T novo (com ID = 0) e retorna o objeto.
     */
    protected abstract T criarNovo();

    /**
     * Exibe o menu de CRUD para a entidade T até o usuário escolher “Voltar”.
     */
    public void exibir() {
        while (true) {
            limparConsole();
            System.out.println("--- Gerenciar " + titulo + " ---");
            System.out.println("1. Cadastrar novo " + titulo.toLowerCase());
            System.out.println("2. Listar todos os " + titulo.toLowerCase());
            System.out.println("3. Buscar " + titulo.toLowerCase() + " por ID");
            System.out.println("4. Atualizar " + titulo.toLowerCase());
            System.out.println("5. Excluir " + titulo.toLowerCase());
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            int op = lerInt();
            switch (op) {
                case 1:
                    // (1) chamar o método abstrato do filho para criar T novo
                    T novo = criarNovo();
                    cadastrador.accept(novo);
                    System.out.println(titulo + " cadastrado: " + novo);
                    aguardarEnter();
                    break;

                case 2:
                    // (2) lista todos
                    List<T> todos = listaTodos.apply(null);
                    if (todos.isEmpty()) {
                        System.out.println("Nenhum " + titulo.toLowerCase() + " cadastrado.");
                    } else {
                        todos.forEach(System.out::println);
                    }
                    aguardarEnter();
                    break;

                case 3:
                    // (3) buscar por ID
                    System.out.print("ID: ");
                    int idBusca = lerInt();
                    T achado = buscador.apply(idBusca);
                    if (achado == null) {
                        System.out.println(titulo + " não encontrado.");
                    } else {
                        System.out.println(achado);
                    }
                    aguardarEnter();
                    break;

                case 4:
                    // (4) atualizar
                    System.out.print("ID a atualizar: ");
                    int idUpd = lerInt();
                    T existente = buscador.apply(idUpd);
                    if (existente == null) {
                        System.out.println(titulo + " não encontrado.");
                        aguardarEnter();
                        break;
                    }
                    // Lê os novos campos (o método criarNovo() devolve objeto com ID=0)
                    T modificado = criarNovo();
                    // Tenta ajustar o ID para o mesmo que será atualizado
                    try {
                        modificado.getClass()
                                .getMethod("setId", int.class)
                                .invoke(modificado, idUpd);
                    } catch (Exception ignored) {
                        // se não houver setId(int), assume que o construtor já usa ID como primeiro argumento
                    }
                    atualizador.accept(modificado);
                    System.out.println(titulo + " atualizado: " + modificado);
                    aguardarEnter();
                    break;

                case 5:
                    // (5) excluir
                    System.out.print("ID a excluir: ");
                    int idExc = lerInt();
                    boolean ok = excluidor.apply(idExc);
                    System.out.println(ok
                            ? titulo + " excluído com sucesso."
                            : titulo + " não encontrado.");
                    aguardarEnter();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opção inválida!");
                    aguardarEnter();
            }
        }
    }

    /** Lê uma linha do console e converte em inteiro, repetindo até obter um valor válido. */
    protected int lerInt() {
        while (true) {
            try {
                String linha = scanner.nextLine();
                return Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número: ");
            }
        }
    }

    /** Pausa até o usuário pressionar ENTER. */
    protected void aguardarEnter() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    /** “Limpa” o console imprimindo várias linhas em branco. */
    protected void limparConsole() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }
}
