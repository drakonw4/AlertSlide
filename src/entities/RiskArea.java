package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma área de risco com cobertura vegetal, sensores e nível de risco.
 */
public class RiskArea extends Entity {
    private String coordinates;
    private RiskLevel riskLevel;
    private List<Sensor> sensors = new ArrayList<>();

    public RiskArea(int id, String nome, String coordinates, RiskLevel riskLevel) {
        super(id, nome);
        setCoordinates(coordinates);
        setRiskLevel(riskLevel);
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        if (coordinates == null || coordinates.trim().isEmpty()) {
            throw new IllegalArgumentException("Coordinates não podem ser nulas ou vazias.");
        }
        this.coordinates = coordinates;
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

    public List<Sensor> getSensors() {
        return sensors;
    }

    /**
     * Adiciona um Sensor a esta área, garantindo associação bidirecional.
     */
    public void addSensor(Sensor s) {
        if (s == null) {
            return;
        }
        if (!sensors.contains(s)) {
            sensors.add(s);
            s.setRiskArea(this);
        }
    }

    /**
     * Remove um Sensor desta área e desassocia no próprio objeto Sensor.
     */
    public void removeSensor(Sensor s) {
        if (s == null) {
            return;
        }
        if (sensors.remove(s)) {
            s.setRiskArea(null);
        }
    }

    @Override
    public String toString() {
        return "RiskArea[" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", riskLevel=" + riskLevel +
                ", sensores=" + sensors.size() +
                ']';
    }
}
