package menu;

import entities.Shelter;
import services.ShelterService;

import java.util.List;

/**
 * Menu de CRUD para a entidade Shelter, estendendo CrudMenu<Shelter>.
 */
public class ShelterMenu extends CrudMenu<Shelter> {

    private final ShelterService shelterService;

    public ShelterMenu(ShelterService shelterService) {
        super(
                "Abrigo",
                // listaTodos
                v -> shelterService.listar(),
                // buscador
                id -> shelterService.buscarPorId(id),
                // atualizador
                obj -> shelterService.atualizar(obj),
                // excluidor
                id -> shelterService.excluir(id),
                // cadastrador
                obj -> shelterService.cadastrar(obj)
        );
        this.shelterService = shelterService;
    }

    @Override
    protected Shelter criarNovo() {
        limparConsole();
        System.out.println(">>> Dados do Abrigo >>>");
        System.out.print("Nome do abrigo: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String address = scanner.nextLine();
        System.out.print("Capacidade máxima: ");
        int cap = lerInt();

        // Construtor de Shelter: Shelter(int id, String nome, String address, int capacityMax, boolean active)
        return new Shelter(0, nome, address, cap, true);
    }
}
