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
        return 0 + (random.nextDouble() * 90); // 0° a 90°
    }

    public double[] simularLeituraSensores() {
        double umidade = gerarUmidade();
        double pressao = gerarPressao();
        double inclinacao = gerarInclinacao();

        System.out.println("\n📡 Leituras simuladas dos sensores:");
        System.out.printf("- Umidade: %.2f %%\n", umidade);
        System.out.printf("- Pressão: %.2f hPa\n", pressao);
        System.out.printf("- Inclinação: %.2f °\n", inclinacao);

        return new double[] { umidade, pressao, inclinacao };
    }
}
