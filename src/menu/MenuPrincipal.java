package menu;

import services.*;
import java.util.Scanner;

public class MenuPrincipal {

    private final SensorService sensorService;
    private final RiskAnalysisService riskService;
    private final AlertService alertService;
    private final EvacuationService evacuationService;
    private final UserService userService;
    private final RiskAreaService areaService;
    private final ShelterService shelterService;

    private final Scanner scanner;
    private double[] ultimaLeitura;
    private String ultimoNivelRisco;

    public MenuPrincipal() {
        this.sensorService = new SensorService();
        this.riskService = new RiskAnalysisService();
        this.alertService = new AlertService();
        this.evacuationService = new EvacuationService();
        this.userService = new UserService();
        this.areaService = new RiskAreaService();
        this.shelterService = new ShelterService();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== ALERTSLIDE: MENU PRINCIPAL ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Simular sensores");
            System.out.println("4. Analisar risco");
            System.out.println("5. Emitir alerta");
            System.out.println("6. Executar evacuação");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> {
                    ultimaLeitura = sensorService.simularLeituraSensores();
                }
                case 4 -> {
                    if (ultimaLeitura == null) {
                        System.out.println("⚠️ É necessário simular os sensores antes.");
                    } else {
                        double umidade = ultimaLeitura[0];
                        double pressao = ultimaLeitura[1];
                        double inclinacao = ultimaLeitura[2];
                        ultimoNivelRisco = riskService.calcularNivelRisco(umidade, pressao, inclinacao);
                        System.out.println("Risco calculado: " + ultimoNivelRisco);
                    }
                }
                case 5 -> {
                    if (ultimoNivelRisco == null) {
                        System.out.println("⚠️ Nenhum risco calculado. Simule sensores e calcule o risco primeiro.");
                    } else {
                        alertService.emitirAlerta(ultimoNivelRisco);
                    }
                }
                case 6 -> {
                    evacuationService.iniciarEvacuacao("Área 12");
                }
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.println("\n--- MENU DE CADASTRO ---");
        System.out.println("1. Usuário");
        System.out.println("2. Sensor");
        System.out.println("3. Área de Risco");
        System.out.println("4. Abrigo");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1 -> userService.cadastrarUsuario(scanner);
            case 2 -> sensorService.cadastrarSensor(scanner);
            case 3 -> areaService.cadastrarArea(scanner);
            case 4 -> shelterService.cadastrarAbrigo(scanner);
            default -> System.out.println("Opção inválida!");
        }
    }

    private void listar() {
        System.out.println("\n--- MENU DE LISTAGEM ---");
        System.out.println("1. Usuários");
        System.out.println("2. Sensores");
        System.out.println("3. Áreas de Risco");
        System.out.println("4. Abrigos");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1 -> userService.listarUsuarios();
            case 2 -> sensorService.listarSensores();
            case 3 -> areaService.listarAreas();
            case 4 -> shelterService.listarAbrigos();
            default -> System.out.println("Opção inválida!");
        }
    }
}
