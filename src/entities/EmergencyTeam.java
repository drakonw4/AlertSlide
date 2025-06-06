package entities;

/**
 * Modela uma equipe de resposta a emergências, com tipo, status de disponibilidade e contato.
 */
public class EmergencyTeam extends Entity {
    private TeamType type;
    private boolean available;
    private String contact;

    public EmergencyTeam(int id, String nome, TeamType type, String contact, boolean available) {
        super(id, nome);
        setType(type);
        setContact(contact);
        this.available = available;
    }

    public TeamType getType() {
        return type;
    }

    public void setType(TeamType type) {
        if (type == null) {
            throw new IllegalArgumentException("TeamType não pode ser nulo.");
        }
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void dispatch() {
        if (!available) {
            throw new IllegalStateException("Equipe já está em missão.");
        }
        this.available = false;
    }

    public void returnToBase() {
        if (available) {
            throw new IllegalStateException("Equipe já está na base.");
        }
        this.available = true;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        if (contact == null || contact.trim().isEmpty()) {
            throw new IllegalArgumentException("Contato não pode ser nulo ou vazio.");
        }
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "EmergencyTeam[" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", type=" + type +
                ", available=" + available +
                ", contact='" + contact + '\'' +
                ']';
    }
}
