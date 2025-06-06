package services;

import common.AbstractCrudService;
import entities.Shelter;

/**
 * Serviço genérico de CRUD para Shelter.
 * - Herdado de AbstractCrudService para manter lista e controle de IDs.
 * - Inclui validações de capacidade e métodos de alocação/desalocação de usuários.
 */
public class ShelterService extends AbstractCrudService<Shelter> {

    @Override
    protected int getId(Shelter s) {
        return s.getId();
    }

    @Override
    protected void setId(Shelter s, int id) {
        s.setId(id);
    }

    /**
     * Atualiza um abrigo existente:
     * - Ajusta nome, endereço, capacidade máxima e status ativo.
     * - A lógica do setter de Shelter garante que, se capacityMax for reduzido abaixo de capacityAtual,
     *   capacityAtual será automaticamente ajustado.
     *
     * @param s Objeto Shelter contendo id e novos valores para nome, address, capacityMax e active.
     * @return o objeto existente atualizado, ou null se não encontrado.
     */
    @Override
    public Shelter atualizar(Shelter s) {
        Shelter existente = buscarPorId(s.getId());
        if (existente == null) {
            return null;
        }

        // Atualiza nome
        existente.setNome(s.getNome());

        // Atualiza endereço
        existente.setAddress(s.getAddress());

        // Atualiza capacidade máxima (o setter ajusta capacityAtual se necessário)
        existente.setCapacityMax(s.getCapacityMax());

        // Atualiza status ativo/inativo
        existente.setActive(s.isActive());

        return existente;
    }

    /**
     * Tenta alocar um usuário no abrigo:
     * - Se o abrigo não existir, retorna false.
     * - Se o abrigo estiver inativo ou sem vagas, dispara exceção via entrarUsuario().
     *
     * @param shelterId ID do abrigo onde o usuário deve entrar.
     * @return true se alocado com sucesso, false se abrigo não existir.
     * @throws IllegalStateException se abrigo inativo ou lotado.
     */
    public boolean alocarUsuario(int shelterId) {
        Shelter s = buscarPorId(shelterId);
        if (s == null) {
            return false;
        }
        // lançar IllegalStateException caso inativo ou lotado
        s.entrarUsuario();
        return true;
    }

    /**
     * Tenta desalocar um usuário do abrigo:
     * - Se o abrigo não existir, retorna false.
     * - Se não houver ninguém para sair, entrarUsuario() (no método sairUsuario()) lança exceção.
     *
     * @param shelterId ID do abrigo de onde o usuário deve sair.
     * @return true se desalocado com sucesso, false se abrigo não existir.
     * @throws IllegalStateException se não houver ninguém para sair.
     */
    public boolean desalocarUsuario(int shelterId) {
        Shelter s = buscarPorId(shelterId);
        if (s == null) {
            return false;
        }
        s.sairUsuario();
        return true;
    }
}
