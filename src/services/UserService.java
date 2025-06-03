package services;

import entities.User;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private ArrayList<User> usuarios = new ArrayList<>();
    private int proximoId = 1;
    private Scanner scanner = new Scanner(System.in);

    public void abrirMenu() {
        int opcao;

        do {
            System.out.println("\n--- MENU DE USUÁRIOS ---");
            System.out.println("1. Cadastrar usuário");
            System.out.println("2. Listar usuários");
            System.out.println("3. Buscar usuário por ID");
            System.out.println("4. Atualizar usuário");
            System.out.println("5. Remover usuário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> atualizar();
                case 5 -> remover();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        System.out.print("Nome do usuário: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Telefone: ");
        String phone = scanner.nextLine();

        System.out.print("Endereço: ");
        String address = scanner.nextLine();

        User user = new User(proximoId++, nome, cpf, phone, address);
        usuarios.add(user);
        System.out.println("✅ Usuário cadastrado com sucesso: " + user);
    }

    private void listar() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            System.out.println("\n📋 Lista de usuários:");
            for (User u : usuarios) {
                System.out.println(u);
            }
        }
    }

    private void buscar() {
        System.out.print("ID do usuário a buscar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (User u : usuarios) {
            if (u.getId() == id) {
                System.out.println("🔍 Usuário encontrado: " + u);
                return;
            }
        }

        System.out.println("Usuário não encontrado.");
    }

    private void atualizar() {
        System.out.print("ID do usuário a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (User u : usuarios) {
            if (u.getId() == id) {
                System.out.print("Novo nome: ");
                String nome = scanner.nextLine();

                System.out.print("Novo CPF: ");
                String cpf = scanner.nextLine();

                System.out.print("Novo telefone: ");
                String phone = scanner.nextLine();

                System.out.print("Novo endereço: ");
                String address = scanner.nextLine();

                u.setNome(nome);
                u.setCpf(cpf);
                u.setPhone(phone);
                u.setAddress(address);

                System.out.println("✏️ Usuário atualizado: " + u);
                return;
            }
        }

        System.out.println("Usuário não encontrado.");
    }

    private void remover() {
        System.out.print("ID do usuário a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (User u : usuarios) {
            if (u.getId() == id) {
                usuarios.remove(u);
                System.out.println("🗑️ Usuário removido com sucesso.");
                return;
            }
        }

        System.out.println("Usuário não encontrado.");
    }
}
