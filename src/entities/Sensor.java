package entities;

public class Sensor extends Entity {
    private SensorType type;
    private double leituraAtual;
    private RiskArea riskArea;

    public Sensor(int id, String nome, SensorType type, double leituraAtual, RiskArea riskArea) {
        super(id, nome);
        setType(type);
        setLeituraAtual(leituraAtual);
        setRiskArea(riskArea);
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        if (type == null) {
            throw new IllegalArgumentException("Tipo de sensor não pode ser nulo.");
        }
        this.type = type;
    }

    public double getLeituraAtual() {
        return leituraAtual;
    }

    public void setLeituraAtual(double leituraAtual) {
        if (leituraAtual < 0) {
            throw new IllegalArgumentException("Leitura atual não pode ser negativa.");
        }
        this.leituraAtual = leituraAtual;
    }

    public RiskArea getRiskArea() {
        return riskArea;
    }

    public void setRiskArea(RiskArea riskArea) {
        if (this.riskArea != null) {
            this.riskArea.removeSensor(this);
        }
        this.riskArea = riskArea;
        if (riskArea != null) {
            riskArea.addSensor(this);
        }
    }

    @Override
    public String toString() {
        String areaNome = (riskArea != null) ? riskArea.getNome() : "Nenhuma";
        return "Sensor[" +
                "id=" + id +
                ", tipo=" + type +
                ", leitura=" + leituraAtual +
                ", área=" + areaNome +
                ']';
    }
}
