package services;

import common.AbstractCrudService;
import entities.User;

import java.util.List;

public class UserService extends AbstractCrudService<User> {

    @Override
    protected int getId(User u) {
        return u.getId();
    }

    @Override
    protected void setId(User u, int id) {
        u.setId(id);
    }

    /**
     * Atualiza um usuário existente, validando CPF duplicado.
     * @param u Objeto User contendo id e novos valores para nome, cpf, phone e address.
     * @return o objeto existente atualizado, ou null se não encontrado.
     */
    @Override
    public User atualizar(User u) {
        // 1) Buscar usuário existente pelo ID
        User existente = buscarPorId(u.getId());
        if (existente == null) {
            return null;
        }

        // 2) Validar CPF duplicado
        for (User outro : itens) {
            if (outro.getCpf().equals(u.getCpf()) && outro.getId() != u.getId()) {
                throw new IllegalArgumentException("CPF já cadastrado em outro usuário.");
            }
        }

        // 3) Atualizar campos obrigatórios
        existente.setNome(u.getNome());
        existente.setCpf(u.getCpf());
        existente.setPhone(u.getPhone());
        existente.setAddress(u.getAddress());
        return existente;
    }
}
