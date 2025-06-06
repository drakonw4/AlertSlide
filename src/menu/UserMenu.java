package menu;

import entities.User;
import services.UserService;

import java.util.List;

/**
 * Menu de CRUD para a entidade User, estendendo CrudMenu<User>.
 */
public class UserMenu extends CrudMenu<User> {

    private final UserService userService;

    public UserMenu(UserService userService) {
        // Chama super sem Supplier<T>, apenas referências aos métodos do serviço de User
        super(
                "Usuário",
                // listaTodos: equivalente a () -> userService.listar()
                v -> userService.listar(),
                // buscador: equivalent a id -> userService.buscarPorId(id)
                id -> userService.buscarPorId(id),
                // atualizador: obj -> userService.atualizar(obj)
                obj -> userService.atualizar(obj),
                // excluidor: id -> userService.excluir(id)
                id -> userService.excluir(id),
                // cadastrador: obj -> userService.cadastrar(obj)
                obj -> userService.cadastrar(obj)
        );
        this.userService = userService;
    }

    @Override
    protected User criarNovo() {
        limparConsole();
        System.out.println(">>> Dados do Usuário >>>");

        // 1. Lê o nome (não pode ser vazio)
        String nome;
        while (true) {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (!nome.isEmpty()) {
                break;
            }
            System.out.println("Nome não pode ser vazio.");
        }

        // 2. Lê o CPF (não pode ser vazio)
        String cpf;
        while (true) {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().trim();
            if (!cpf.isEmpty()) {
                break;
            }
            System.out.println("CPF não pode ser vazio.");
        }

        // 3. Lê o telefone (não pode ser vazio)
        String phone;
        while (true) {
            System.out.print("Telefone: ");
            phone = scanner.nextLine().trim();
            if (!phone.isEmpty()) {
                break;
            }
            System.out.println("Telefone não pode ser vazio.");
        }

        // 4. Lê o endereço (não pode ser vazio)
        String address;
        while (true) {
            System.out.print("Endereço: ");
            address = scanner.nextLine().trim();
            if (!address.isEmpty()) {
                break;
            }
            System.out.println("Endereço não pode ser vazio.");
        }

        // 5. Retorna o User (id temporário = 0, o serviço atribui o real)
        return new User(0, nome, cpf, phone, address);
    }
}
