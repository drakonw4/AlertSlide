package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractCrudService<T> implements CrudService<T> {
    protected List<T> itens = new ArrayList<>();
    protected int proxId = 1;

    @Override
    public T cadastrar(T obj) {
        setId(obj, proxId++);
        itens.add(obj);
        return obj;
    }

    @Override
    public List<T> listar() {
        return Collections.unmodifiableList(itens);
    }

    @Override
    public T buscarPorId(int id) {
        for (T item : itens) {
            if (getId(item) == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean excluir(int id) {
        Iterator<T> it = itens.iterator();
        while (it.hasNext()) {
            T item = it.next();
            if (getId(item) == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    // Só declara: cada service concreto define como atualizar e como acessar o ID.
    @Override
    public abstract T atualizar(T obj);

    protected abstract int getId(T obj);
    protected abstract void setId(T obj, int id);
}
