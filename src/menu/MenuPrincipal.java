package menu;

import services.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final SensorService sensorService = new SensorService();
    private final ShelterService shelterService = new ShelterService();
    private final EmergencyTeamService emergencyTeamService = new EmergencyTeamService();
    private final RiskAnalysisService riskAnalysisService = new RiskAnalysisService();
    private final AlertService alertService = new AlertService();
    private final EvacuationService evacuationService = new EvacuationService();

    public void exibirMenu() {
        int opcao;
        do {
            limparConsole();
            System.out.println("\n===== ALERTSLIDE - MENU PRINCIPAL =====");
            System.out.println("1. Gerenciar Usuarios");
            System.out.println("2. Gerenciar Sensores");
            System.out.println("3. Gerenciar Abrigos");
            System.out.println("4. Gerenciar Equipes de Emergencia");
            System.out.println("5. Iniciar Simulacao de Risco");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida. Digite apenas numeros.");
                scanner.nextLine();
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> menuUsuarios();
                case 2 -> menuSensores();
                case 3 -> menuAbrigos();
                case 4 -> menuEquipes();
                case 5 -> iniciarSimulacao();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> {
                    System.out.println("Opcao invalida.");
                    aguardarEnter();
                }
            }
        } while (opcao != 0);
    }

    private void iniciarSimulacao() {
        limparConsole();
        System.out.println("\n>>> Iniciando simulacao de risco...");

        int ciclos = 5;
        for (int i = 1; i <= ciclos; i++) {
            System.out.println("\n--- CICLO " + i + " ---");

            riskAnalysisService.analisarSensores();
            alertService.verificarENotificar();
            evacuationService.evacuacaoEmergencial();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Simulacao interrompida.");
            }
        }

        System.out.println("\n>>> Simulacao finalizada.");
        aguardarEnter();
    }

    private void menuUsuarios() {
        int opcao;
        do {
            limparConsole();
            System.out.println("\n--- Gerenciar Usuarios ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida.");
                scanner.nextLine();
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> {
                    userService.cadastrar(scanner);
                    aguardarEnter();
                }
                case 2 -> {
                    userService.listar();
                    aguardarEnter();
                }
                case 3 -> {
                    userService.buscar(scanner);
                    aguardarEnter();
                }
                case 4 -> {
                    userService.atualizar(scanner);
                    aguardarEnter();
                }
                case 5 -> {
                    userService.excluir(scanner);
                    aguardarEnter();
                }
                case 0 -> {}
                default -> {
                    System.out.println("Opcao invalida.");
                    aguardarEnter();
                }
            }
        } while (opcao != 0);
    }

    private void menuSensores() {
        int opcao;
        do {
            limparConsole();
            System.out.println("\n--- Gerenciar Sensores ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida.");
                scanner.nextLine();
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> {
                    sensorService.cadastrar(scanner);
                    aguardarEnter();
                }
                case 2 -> {
                    sensorService.listar();
                    aguardarEnter();
                }
                case 3 -> {
                    sensorService.buscar(scanner);
                    aguardarEnter();
                }
                case 4 -> {
                    sensorService.atualizar(scanner);
                    aguardarEnter();
                }
                case 5 -> {
                    sensorService.excluir(scanner);
                    aguardarEnter();
                }
                case 0 -> {}
                default -> {
                    System.out.println("Opcao invalida.");
                    aguardarEnter();
                }
            }
        } while (opcao != 0);
    }

    private void menuAbrigos() {
        int opcao;
        do {
            limparConsole();
            System.out.println("\n--- Gerenciar Abrigos ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida.");
                scanner.nextLine();
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> {
                    shelterService.cadastrar(scanner);
                    aguardarEnter();
                }
                case 2 -> {
                    shelterService.listar();
                    aguardarEnter();
                }
                case 3 -> {
                    shelterService.buscar(scanner);
                    aguardarEnter();
                }
                case 4 -> {
                    shelterService.atualizar(scanner);
                    aguardarEnter();
                }
                case 5 -> {
                    shelterService.excluir(scanner);
                    aguardarEnter();
                }
                case 0 -> {}
                default -> {
                    System.out.println("Opcao invalida.");
                    aguardarEnter();
                }
            }
        } while (opcao != 0);
    }

    private void menuEquipes() {
        int opcao;
        do {
            limparConsole();
            System.out.println("\n--- Gerenciar Equipes de Emergencia ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada invalida.");
                scanner.nextLine();
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> {
                    emergencyTeamService.cadastrar(scanner);
                    aguardarEnter();
                }
                case 2 -> {
                    emergencyTeamService.listar();
                    aguardarEnter();
                }
                case 3 -> {
                    emergencyTeamService.buscar(scanner);
                    aguardarEnter();
                }
                case 4 -> {
                    emergencyTeamService.atualizar(scanner);
                    aguardarEnter();
                }
                case 5 -> {
                    emergencyTeamService.excluir(scanner);
                    aguardarEnter();
                }
                case 0 -> {}
                default -> {
                    System.out.println("Opcao invalida.");
                    aguardarEnter();
                }
            }
        } while (opcao != 0);
    }

    private void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Nao foi possivel limpar o console.");
        }
    }

    private void aguardarEnter() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
}
