package services;

import common.AbstractCrudService;
import entities.EmergencyTeam;
import entities.TeamType;

/**
 * Serviço genérico de CRUD para EmergencyTeam.
 */
public class EmergencyTeamService extends AbstractCrudService<EmergencyTeam> {

    @Override
    protected int getId(EmergencyTeam t) {
        return t.getId();
    }

    @Override
    protected void setId(EmergencyTeam t, int id) {
        t.setId(id);
    }

    /**
     * Atualiza uma equipe de emergência existente:
     * - Ajusta nome, tipo, contato e status de disponibilidade.
     *
     * @param t Objeto EmergencyTeam contendo id e novos valores.
     * @return o objeto existente atualizado, ou null se não encontrado.
     */
    @Override
    public EmergencyTeam atualizar(EmergencyTeam t) {
        EmergencyTeam existente = buscarPorId(t.getId());
        if (existente == null) {
            return null;
        }

        // Atualiza nome
        existente.setNome(t.getNome());

        // Atualiza tipo de equipe
        existente.setType(t.getType());

        // Atualiza contato
        existente.setContact(t.getContact());

        // Atualiza disponibilidade
        if (t.isAvailable() && !existente.isAvailable()) {
            // se estava indisponível, retorna para a base
            existente.returnToBase();
        } else if (!t.isAvailable() && existente.isAvailable()) {
            // se estava disponível, despacha equipe
            existente.dispatch();
        }
        return existente;
    }

    /**
     * Despacha uma equipe, se disponível.
     *
     * @param teamId ID da equipe a ser despachada.
     * @return true se despachada com sucesso, false se não existir ou já estiver em missão.
     */
    public boolean dispatchTeam(int teamId) {
        EmergencyTeam t = buscarPorId(teamId);
        if (t == null || !t.isAvailable()) {
            return false;
        }
        t.dispatch();
        return true;
    }

    /**
     * Retorna uma equipe para a base, se estiver em missão.
     *
     * @param teamId ID da equipe a retornar.
     * @return true se retornou com sucesso, false se não existir ou já estiver na base.
     */
    public boolean returnTeam(int teamId) {
        EmergencyTeam t = buscarPorId(teamId);
        if (t == null || t.isAvailable()) {
            return false;
        }
        t.returnToBase();
        return true;
    }
}
