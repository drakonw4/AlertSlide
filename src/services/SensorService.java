package services;

import java.util.Random;

public class SensorService {
    private Random random = new Random();

    public double gerarUmidade() {
        return 30 + (random.nextDouble() * 70); // 30% a 100%
    }

    public double gerarPressao() {
        return 800 + (random.nextDouble() * 300); // 800 hPa a 1100 hPa
    }

    public double gerarInclinacao() {
        return 0 + (random.nextDouble() * 90); // 0º a 90º
    }

    public String gerarRelatorioSensores() {
        return "Umidade: " + gerarUmidade() + "% | Pressão: " + gerarPressao() + " hPa | Inclinação: " + gerarInclinacao() + "°";
    }
}
