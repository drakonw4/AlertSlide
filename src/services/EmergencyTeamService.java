package services;

import entities.EmergencyTeam;

import java.util.ArrayList;
import java.util.Scanner;

public class EmergencyTeamService {
    private final ArrayList<EmergencyTeam> teams = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void addTeam() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // limpar buffer

        System.out.print("Nome da equipe: ");
        String name = scanner.nextLine();

        System.out.print("Função (ex: Bombeiros, Defesa Civil): ");
        String role = scanner.nextLine();

        System.out.print("Está disponível? (true/false): ");
        boolean available = scanner.nextBoolean();

        EmergencyTeam team = new EmergencyTeam(id, name, role, available);
        teams.add(team);
        System.out.println("✔ Equipe cadastrada com sucesso.\n");
    }

    public void listTeams() {
        if (teams.isEmpty()) {
            System.out.println("❌ Nenhuma equipe cadastrada.\n");
            return;
        }
        System.out.println("🚨 Lista de equipes de emergência:");
        for (EmergencyTeam t : teams) {
            System.out.println(t);
        }
    }

    public EmergencyTeam findTeamById(int id) {
        for (EmergencyTeam t : teams) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public void updateTeam() {
        System.out.print("Digite o ID da equipe: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        EmergencyTeam team = findTeamById(id);

        if (team != null) {
            System.out.print("Nova função: ");
            team.setRole(scanner.nextLine());

            System.out.print("Está disponível? (true/false): ");
            team.setAvailable(scanner.nextBoolean());

            System.out.println("✔ Equipe atualizada com sucesso.\n");
        } else {
            System.out.println("❌ Equipe não encontrada.\n");
        }
    }

    public void removeTeam() {
        System.out.print("Digite o ID da equipe a ser removida: ");
        int id = scanner.nextInt();
        EmergencyTeam team = findTeamById(id);
        if (team != null) {
            teams.remove(team);
            System.out.println("✔ Equipe removida com sucesso.\n");
        } else {
            System.out.println("❌ Equipe não encontrada.\n");
        }
    }

    public ArrayList<EmergencyTeam> getTeams() {
        return teams;
    }
}
