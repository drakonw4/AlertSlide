package services;

import entities.Alert;
import entities.Shelter;
import entities.User;
import entities.EmergencyTeam;

import java.util.List;

/**
 * Serviço responsável por coordenar todo o processo de evacuação:
 * - Notificar usuários
 * - Alocar usuários em abrigos disponíveis
 * - Despachar equipes de emergência
 */
public class EvacuationService {

    private final UserService userService;
    private final ShelterService shelterService;
    private final EmergencyTeamService emergencyTeamService;
    private final AlertService alertService;

    /**
     * Constrói o serviço de evacuação com todas as dependências necessárias.
     *
     * @param userService            serviço de gerenciamento de usuários
     * @param shelterService         serviço de gerenciamento de abrigos
     * @param emergencyTeamService   serviço de gerenciamento de equipes de emergência
     * @param alertService           serviço de gerenciamento de alertas
     */
    public EvacuationService(UserService userService,
                             ShelterService shelterService,
                             EmergencyTeamService emergencyTeamService,
                             AlertService alertService) {
        this.userService = userService;
        this.shelterService = shelterService;
        this.emergencyTeamService = emergencyTeamService;
        this.alertService = alertService;
    }

    /**
     * Executa o protocolo de evacuação baseado em um alerta existente.
     * 1) Recupera o alerta pelo ID.
     * 2) Notifica TODOS os usuários cadastrados sobre o alerta.
     * 3) Tenta alocar cada usuário em um abrigo disponível (ativo e com vaga).
     * 4) Despacha as primeiras equipes de emergência disponíveis para a área.
     *
     * @param alertId ID do alerta que dispara a evacuação.
     */
    public void evacuar(int alertId) {
        // 1) Buscar o alerta
        Alert alert = alertService.buscarPorId(alertId);
        if (alert == null) {
            System.out.println("❌ Alerta com ID " + alertId + " não encontrado. Evacuação abortada.");
            return;
        }

        String areaNome = alert.getArea().getNome();
        System.out.println("\n🏃 Iniciando evacuação para a área: " + areaNome);

        // 2) Notificar todos os usuários
        List<User> todosUsuarios = userService.listar();
        if (todosUsuarios.isEmpty()) {
            System.out.println("⚠️ Não há usuários cadastrados para notificar.");
        } else {
            System.out.println("\n📣 Notificando usuários:");
            for (User u : todosUsuarios) {
                System.out.printf("   - Notificando %s (CPF: %s, Telefone: %s) sobre alerta em %s%n",
                        u.getNome(), u.getCpf(), u.getPhone(), areaNome);
            }
        }

        // 3) Alocar cada usuário em um abrigo disponível
        List<Shelter> todosAbrigos = shelterService.listar();
        if (todosAbrigos.isEmpty()) {
            System.out.println("\n⚠️ Não há abrigos cadastrados para alocação.");
        } else {
            System.out.println("\n🏠 Alocando usuários em abrigos disponíveis:");
            for (User u : todosUsuarios) {
                boolean alocado = false;
                for (Shelter s : todosAbrigos) {
                    if (s.isActive() && s.getCapacityAtual() < s.getCapacityMax()) {
                        // tenta incluir usuário
                        try {
                            s.entrarUsuario();
                            System.out.printf("   ✔ %s alocado no abrigo %s (ocupação: %d/%d)%n",
                                    u.getNome(), s.getNome(), s.getCapacityAtual(), s.getCapacityMax());
                            alocado = true;
                            break;
                        } catch (IllegalStateException e) {
                            // abrigo cheio ou inativo: continuar procurando outro abrigo
                        }
                    }
                }
                if (!alocado) {
                    System.out.printf("   ❌ %s NÃO pôde ser alocado em nenhum abrigo.%n", u.getNome());
                }
            }
        }

        // 4) Despachar equipes de emergência para a área
        List<EmergencyTeam> todasEquipes = emergencyTeamService.listar();
        if (todasEquipes.isEmpty()) {
            System.out.println("\n⚠️ Não há equipes de emergência cadastradas para despachar.");
        } else {
            System.out.println("\n🚑 Despachando equipes de emergência:");
            for (EmergencyTeam t : todasEquipes) {
                if (t.isAvailable()) {
                    boolean despachou = emergencyTeamService.dispatchTeam(t.getId());
                    if (despachou) {
                        System.out.printf("   ✔ Equipe %s (ID %d) despachada para %s%n",
                                t.getNome(), t.getId(), areaNome);
                    }
                }
            }
        }

        System.out.println("\n✅ Processo de evacuação concluído para a área " + areaNome + ".\n");
    }
}
