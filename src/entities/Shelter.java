package entities;

/**
 * Representa um abrigo para evacuação, com capacidade e status de atividade.
 */
public class Shelter extends Entity {
    private String address;
    private int capacityMax;
    private int capacityAtual;
    private boolean active;

    public Shelter(int id, String nome, String address, int capacityMax, boolean active) {
        super(id, nome);
        setAddress(address);
        setCapacityMax(capacityMax);
        this.capacityAtual = 0; // Inicialmente vazio
        setActive(active);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do abrigo não pode ser nulo ou vazio.");
        }
        this.address = address;
    }

    public int getCapacityMax() {
        return capacityMax;
    }

    public void setCapacityMax(int capacityMax) {
        if (capacityMax < 0) {
            throw new IllegalArgumentException("Capacidade máxima não pode ser negativa.");
        }
        this.capacityMax = capacityMax;
        // Ajusta capacityAtual se necessário
        if (this.capacityAtual > capacityMax) {
            this.capacityAtual = capacityMax;
        }
    }

    public int getCapacityAtual() {
        return capacityAtual;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Incrementa em 1 a quantidade de usuários no abrigo, se não ultrapassar o máximo.
     */
    public void entrarUsuario() {
        if (!active) {
            throw new IllegalStateException("Não é possível entrar: abrigo inativo.");
        }
        if (capacityAtual + 1 > capacityMax) {
            throw new IllegalStateException("Capacidade máxima atingida. Não é possível adicionar novo usuário.");
        }
        capacityAtual++;
    }

    /**
     * Decrementa em 1 a quantidade de usuários, se já houver alguém dentro.
     */
    public void sairUsuario() {
        if (capacityAtual - 1 < 0) {
            throw new IllegalStateException("Não há usuários no abrigo para sair.");
        }
        capacityAtual--;
    }

    @Override
    public String toString() {
        return "Shelter[" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", address='" + address + '\'' +
                ", ocupação=" + capacityAtual + "/" + capacityMax +
                ", ativo=" + active +
                ']';
    }
}
