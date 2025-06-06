package menu;

import services.*;
import entities.RiskLevel;
import entities.Alert;

import java.util.Scanner;

/**
 * Menu principal da aplicação AlertSlide.
 */
public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);

    // Instanciamos os serviços (cada um herda AbstractCrudService<T> de src/common)
    private final UserService userService = new UserService();
    private final SensorService sensorService = new SensorService();
    private final RiskAreaService riskAreaService = new RiskAreaService();
    private final ShelterService shelterService = new ShelterService();
    private final EmergencyTeamService teamService = new EmergencyTeamService();
    private final RiskAnalysisService riskAnalysisService = new RiskAnalysisService(sensorService);
    private final AlertService alertService = new AlertService();
    private final EvacuationService evacuationService =
            new EvacuationService(userService, shelterService, teamService, alertService);

    // Instanciamos cada menu de CRUD
    private final UserMenu userMenu = new UserMenu(userService);
    private final SensorMenu sensorMenu = new SensorMenu(sensorService, riskAreaService);
    private final RiskAreaMenu riskAreaMenu = new RiskAreaMenu(riskAreaService);
    private final ShelterMenu shelterMenu = new ShelterMenu(shelterService);
    private final EmergencyTeamMenu teamMenu = new EmergencyTeamMenu(teamService);

    public void exibirMenu() {
        while (true) {
            limparConsole();
            System.out.println("===== ALERTSLIDE – MENU PRINCIPAL =====");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Sensores");
            System.out.println("3. Gerenciar Áreas de Risco");
            System.out.println("4. Gerenciar Abrigos");
            System.out.println("5. Gerenciar Equipes de Emergência");
            System.out.println("6. Iniciar Simulação de Risco");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            int opcao = lerInt();
            switch (opcao) {
                case 1:
                    userMenu.exibir();
                    break;
                case 2:
                    sensorMenu.exibir();
                    break;
                case 3:
                    riskAreaMenu.exibir();
                    break;
                case 4:
                    shelterMenu.exibir();
                    break;
                case 5:
                    teamMenu.exibir();
                    break;
                case 6:
                    iniciarSimulacao();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
                    aguardarEnter();
            }
        }
    }

    private void iniciarSimulacao() {
        limparConsole();
        System.out.println(">>> Iniciar Simulação de Risco >>>");
        System.out.println("Pressione ENTER para continuar ou 'q' + ENTER para parar.");
        aguardarEnter();

        while (true) {
            limparConsole();
            System.out.println("=== Nova iteração ===");

            var sensores = sensorService.listar();
            if (sensores.isEmpty()) {
                System.out.println("Nenhum sensor cadastrado. Simulação não pode continuar.");
                aguardarEnter();
                return;
            }
            System.out.println("\nGerando leituras:");
            for (var s : sensores) {
                double valor;
                switch (s.getType()) {
                    case UMIDADE:
                        valor = sensorService.gerarUmidade();
                        break;
                    case PRESSAO:
                        valor = sensorService.gerarPressao();
                        break;
                    case INCLINACAO:
                        valor = sensorService.gerarInclinacao();
                        break;
                    case VEGETACAO:
                        valor = sensorService.gerarUmidade();
                        break;
                    default:
                        valor = 0.0;
                }
                sensorService.simularLeitura(s.getId(), valor);
                System.out.println(sensorService.buscarPorId(s.getId()));
            }

            var areas = riskAreaService.listar();
            if (areas.isEmpty()) {
                System.out.println("\nSem áreas de risco cadastradas. Fim da simulação.");
                aguardarEnter();
                return;
            }
            System.out.println();
            for (var area : areas) {
                double um = Double.NaN, pr = Double.NaN, inc = Double.NaN;
                for (var s : sensores) {
                    if (s.getRiskArea() != null && s.getRiskArea().getId() == area.getId()) {
                        switch (s.getType()) {
                            case UMIDADE:
                                um = s.getLeituraAtual();
                                break;
                            case PRESSAO:
                                pr = s.getLeituraAtual();
                                break;
                            case INCLINACAO:
                                inc = s.getLeituraAtual();
                                break;
                            default:
                                break;
                        }
                    }
                }
                if (Double.isNaN(um))  um = sensorService.gerarUmidade();
                if (Double.isNaN(pr))  pr = sensorService.gerarPressao();
                if (Double.isNaN(inc)) inc = sensorService.gerarInclinacao();

                var nivel = riskAnalysisService.calcularNivelRisco(um, pr, inc);
                System.out.printf(
                        "Área \"%s\" – Nível: %s (U=%.1f, P=%.1f, I=%.1f)%n",
                        area.getNome(), nivel, um, pr, inc
                );

                if (nivel == RiskLevel.ALTO) {
                    var alerta = new Alert(0, area, nivel);
                    alertService.cadastrar(alerta);
                    System.out.println("  → Alerta: " + alerta);
                    evacuationService.evacuar(alerta.getId());
                }
            }

            System.out.print("\nENTER p/ próxima ou 'q'+ENTER p/ parar: ");
            String cmd = scanner.nextLine().trim();
            if (cmd.equalsIgnoreCase("q")) {
                System.out.println("Simulação encerrada.");
                aguardarEnter();
                break;
            }
        }
    }

    private int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número: ");
            }
        }
    }

    private void aguardarEnter() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private void limparConsole() {
        for (int i = 0; i < 20; i++) System.out.println();
    }
}
