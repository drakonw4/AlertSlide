package entities;

public class RiskArea extends Entity {
    private String coordinates;
    private String riskLevel; // Green, Yellow, Orange, Red

    public RiskArea(int id, String name, String coordinates, String riskLevel) {
        super(id, name);
        this.coordinates = coordinates;
        this.riskLevel = riskLevel;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Coordinates: " + coordinates +
                ", Risk Level: " + riskLevel;
    }
}
