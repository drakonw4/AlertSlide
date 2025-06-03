package services;

public class RiskAnalysisService {

    public String calcularNivelRisco(double umidade, double pressao, double inclinacao) {
        double score = 0;

        if (umidade > 80) score += 1;
        if (pressao < 950) score += 1;
        if (inclinacao > 45) score += 2;

        if (score >= 3) return "ALTO";
        else if (score == 2) return "MODERADO";
        else return "BAIXO";
    }

    public String gerarRelatorioRisco(double umidade, double pressao, double inclinacao) {
        String nivel = calcularNivelRisco(umidade, pressao, inclinacao);
        return "Risco: " + nivel + " (U: " + umidade + "% | P: " + pressao + " hPa | I: " + inclinacao + "°)";
    }
}
