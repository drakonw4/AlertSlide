package menu;

import services.*;

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
            System.out.println("\n===== ALERTSLIDE - MENU PRINCIPAL =====");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Sensores");
            System.out.println("3. Gerenciar Abrigos");
            System.out.println("4. Gerenciar Equipes de Emergência");
            System.out.println("5. Iniciar Simulação de Risco");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> menuUsuarios();
                case 2 -> menuSensores();
                case 3 -> menuAbrigos();
                case 4 -> menuEquipes();
                case 5 -> iniciarSimulacao();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void iniciarSimulacao() {
        System.out.println("\n>>> Iniciando simulação de risco...");

        int ciclos = 5; // Número de ciclos simulados
        for (int i = 1; i <= ciclos; i++) {
            System.out.println("\n--- CICLO " + i + " ---");

            riskAnalysisService.analisarSensores();
            alertService.verificarENotificar();
            evacuationService.evacuacaoEmergencial();

            try {
                Thread.sleep(2000); // Pausa de 2 segundos
            } catch (InterruptedException e) {
                System.out.println("Simulação interrompida.");
            }
        }

        System.out.println("\n>>> Simulação finalizada.\n");
    }

    private void menuUsuarios() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Usuários ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> userService.cadastrar(scanner);
                case 2 -> userService.listar();
                case 3 -> userService.buscar(scanner);
                case 4 -> userService.atualizar(scanner);
                case 5 -> userService.excluir(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuSensores() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Sensores ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> sensorService.cadastrar(scanner);
                case 2 -> sensorService.listar();
                case 3 -> sensorService.buscar(scanner);
                case 4 -> sensorService.atualizar(scanner);
                case 5 -> sensorService.excluir(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuAbrigos() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Abrigos ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> shelterService.cadastrar(scanner);
                case 2 -> shelterService.listar();
                case 3 -> shelterService.buscar(scanner);
                case 4 -> shelterService.atualizar(scanner);
                case 5 -> shelterService.excluir(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void menuEquipes() {
        int opcao;
        do {
            System.out.println("\n--- Gerenciar Equipes de Emergência ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar");
            System.out.println("5. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> emergencyTeamService.cadastrar(scanner);
                case 2 -> emergencyTeamService.listar();
                case 3 -> emergencyTeamService.buscar(scanner);
                case 4 -> emergencyTeamService.atualizar(scanner);
                case 5 -> emergencyTeamService.excluir(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
