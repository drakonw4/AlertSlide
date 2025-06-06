package menu;

import entities.RiskArea;
import entities.RiskLevel;
import services.RiskAreaService;

import java.util.List;

/**
 * Menu de CRUD para a entidade RiskArea, estendendo CrudMenu<RiskArea>.
 */
public class RiskAreaMenu extends CrudMenu<RiskArea> {

    private final RiskAreaService riskAreaService;

    public RiskAreaMenu(RiskAreaService riskAreaService) {
        super(
                "Área de Risco",
                // listaTodos
                v -> riskAreaService.listar(),
                // buscador
                id -> riskAreaService.buscarPorId(id),
                // atualizador
                obj -> riskAreaService.atualizar(obj),
                // excluidor
                id -> riskAreaService.excluir(id),
                // cadastrador
                obj -> riskAreaService.cadastrar(obj)
        );
        this.riskAreaService = riskAreaService;
    }

    @Override
    protected RiskArea criarNovo() {
        limparConsole();
        System.out.println(">>> Dados da Área de Risco >>>");
        System.out.print("Nome da área: ");
        String nome = scanner.nextLine();
        System.out.print("Coordenadas (lat,long): ");
        String coords = scanner.nextLine();

        // Escolher o nível via enum RiskLevel
        System.out.println("Escolha o nível de risco:");
        RiskLevel[] niveis = RiskLevel.values();
        for (int i = 0; i < niveis.length; i++) {
            System.out.printf("%d – %s%n", i + 1, niveis[i]);
        }
        System.out.print("Opção: ");
        int idx = lerInt();
        RiskLevel nivel = (idx >= 1 && idx <= niveis.length) ? niveis[idx - 1] : niveis[0];

        // Construtor de RiskArea: RiskArea(int id, String nome, String coordinates, RiskLevel riskLevel)
        return new RiskArea(0, nome, coords, nivel);
    }
}
