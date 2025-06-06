package menu;

import entities.RiskArea;
import entities.Sensor;
import entities.SensorType;
import services.RiskAreaService;
import services.SensorService;

import java.util.List;

/**
 * Menu de CRUD para a entidade Sensor, estendendo CrudMenu<Sensor>.
 */
public class SensorMenu extends CrudMenu<Sensor> {

    private final SensorService sensorService;
    private final RiskAreaService riskAreaService;

    public SensorMenu(SensorService sensorService, RiskAreaService riskAreaService) {
        super(
                "Sensor",
                // listaTodos
                v -> sensorService.listar(),
                // buscador por ID
                id -> sensorService.buscarPorId(id),
                // atualizador
                obj -> sensorService.atualizar(obj),
                // excluidor
                id -> sensorService.excluir(id),
                // cadastrador
                obj -> sensorService.cadastrar(obj)
        );
        this.sensorService = sensorService;
        this.riskAreaService = riskAreaService;
    }

    @Override
    protected Sensor criarNovo() {
        limparConsole();
        System.out.println(">>> Dados do Sensor >>>");
        System.out.print("Nome do sensor: ");
        String nome = scanner.nextLine();

        // Escolher o tipo via enum SensorType
        System.out.println("Escolha o tipo de sensor:");
        SensorType[] tipos = SensorType.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d – %s%n", i + 1, tipos[i]);
        }
        System.out.print("Opção: ");
        int idx = lerInt();
        SensorType tipo = (idx >= 1 && idx <= tipos.length) ? tipos[idx - 1] : tipos[0];

        // Associar opcionalmente a uma área de risco
        System.out.print("Digite a ID da área de risco associada (0 = nenhuma): ");
        int areaId = lerInt();
        RiskArea area = (areaId == 0) ? null : riskAreaService.buscarPorId(areaId);

        // Construtor de Sensor: Sensor(int id, String nome, SensorType type, double leituraAtual, RiskArea riskArea)
        return new Sensor(0, nome, tipo, 0.0, area);
    }
}
