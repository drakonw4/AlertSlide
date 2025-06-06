package entities;

import java.time.LocalDateTime;

/**
 * Registra um alerta gerado para uma área de risco, contendo nível, mensagem e timestamp.
 */
public class Alert extends Entity {
    private RiskArea area;
    private RiskLevel riskLevel;
    private String message;
    private LocalDateTime timestamp;

    public Alert(int id, RiskArea area, RiskLevel riskLevel) {
        super(id, "Alerta_" + id);
        setArea(area);
        setRiskLevel(riskLevel);
        this.timestamp = LocalDateTime.now();
        this.message = String.format("Risco %s em %s", riskLevel, area.getNome());
    }

    public RiskArea getArea() {
        return area;
    }

    public void setArea(RiskArea area) {
        if (area == null) {
            throw new IllegalArgumentException("Area não pode ser nula.");
        }
        this.area = area;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        if (riskLevel == null) {
            throw new IllegalArgumentException("RiskLevel não pode ser nulo.");
        }
        this.riskLevel = riskLevel;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Alert[" +
                "id=" + id +
                ", área=" + area.getNome() +
                ", nível=" + riskLevel +
                ", mensagem=\"" + message + "\"" +
                ", timestamp=" + timestamp +
                ']';
    }
}
