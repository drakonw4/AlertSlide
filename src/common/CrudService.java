package common;

import java.util.List;

public interface CrudService<T> {
    T cadastrar(T obj);
    List<T> listar();
    T buscarPorId(int id);
    T atualizar(T obj);
    boolean excluir(int id);
}
