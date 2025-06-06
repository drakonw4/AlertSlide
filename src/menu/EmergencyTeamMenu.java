package menu;

import entities.EmergencyTeam;
import entities.TeamType;
import services.EmergencyTeamService;

import java.util.List;

/**
 * Menu de CRUD para a entidade EmergencyTeam, estendendo CrudMenu<EmergencyTeam>.
 */
public class EmergencyTeamMenu extends CrudMenu<EmergencyTeam> {

    private final EmergencyTeamService teamService;

    public EmergencyTeamMenu(EmergencyTeamService teamService) {
        super(
                "Equipe de Emergência",
                // listaTodos
                v -> teamService.listar(),
                // buscador
                id -> teamService.buscarPorId(id),
                // atualizador
                obj -> teamService.atualizar(obj),
                // excluidor
                id -> teamService.excluir(id),
                // cadastrador
                obj -> teamService.cadastrar(obj)
        );
        this.teamService = teamService;
    }

    @Override
    protected EmergencyTeam criarNovo() {
        limparConsole();
        System.out.println(">>> Dados da Equipe de Emergência >>>");
        System.out.print("Nome da equipe: ");
        String nome = scanner.nextLine();

        System.out.println("Escolha o tipo de equipe:");
        TeamType[] tipos = TeamType.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d – %s%n", i + 1, tipos[i]);
        }
        System.out.print("Opção: ");
        int idx = lerInt();
        TeamType tipo = (idx >= 1 && idx <= tipos.length) ? tipos[idx - 1] : tipos[0];

        System.out.print("Contato (telefone/e-mail): ");
        String contato = scanner.nextLine();

        // Construtor de EmergencyTeam: EmergencyTeam(int id, String nome, TeamType type, String contact, boolean available)
        return new EmergencyTeam(0, nome, tipo, contato, true);
    }
}
