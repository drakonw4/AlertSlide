package services;

import common.AbstractCrudService;
import entities.Sensor;

import java.util.Random;

/**
 * Serviço genérico de CRUD para Sensor.
 * Inclui também métodos de simulação de leitura (umidade, pressão e inclinação).
 */
public class SensorService extends AbstractCrudService<Sensor> {

    private final Random random = new Random();

    @Override
    protected int getId(Sensor s) {
        return s.getId();
    }

    @Override
    protected void setId(Sensor s, int id) {
        s.setId(id);
    }

    /**
     * Atualiza um sensor existente, validando a área de risco.
     */
    @Override
    public Sensor atualizar(Sensor s) {
        Sensor existente = buscarPorId(s.getId());
        if (existente == null) {
            return null;
        }
        if (s.getRiskArea() == null) {
            throw new IllegalArgumentException("Área de risco inválida.");
        }
        existente.setNome(s.getNome());
        existente.setType(s.getType());
        existente.setLeituraAtual(s.getLeituraAtual());
        existente.setRiskArea(s.getRiskArea());
        return existente;
    }

    /**
     * Método extra para simular leitura manual de um sensor.
     */
    public void simularLeitura(int sensorId, double valor) {
        Sensor s = buscarPorId(sensorId);
        if (s == null) {
            throw new IllegalArgumentException("Sensor não encontrado.");
        }
        s.setLeituraAtual(valor);
    }

    /**
     * Gera uma leitura simulada de umidade (30% a 100%).
     */
    public double gerarUmidade() {
        return 30 + (random.nextDouble() * 70);
    }

    /**
     * Gera uma leitura simulada de pressão (800 hPa a 1100 hPa).
     */
    public double gerarPressao() {
        return 800 + (random.nextDouble() * 300);
    }

    /**
     * Gera uma leitura simulada de inclinação (0° a 90°).
     */
    public double gerarInclinacao() {
        return random.nextDouble() * 90;
    }
}
