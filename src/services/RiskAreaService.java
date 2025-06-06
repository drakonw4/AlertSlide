package services;

import common.AbstractCrudService;
import entities.RiskArea;
import entities.Sensor;

/**
 * Serviço genérico de CRUD para RiskArea.
 * - Herda ArrayList<RiskArea> e controle de IDs de AbstractCrudService.
 * - Inclui método extra para associar sensores à área, mantendo relação bidirecional.
 */
public class RiskAreaService extends AbstractCrudService<RiskArea> {

    @Override
    protected int getId(RiskArea r) {
        return r.getId();
    }

    @Override
    protected void setId(RiskArea r, int id) {
        r.setId(id);
    }

    /**
     * Atualiza uma área de risco existente:
     * - Ajusta nome, coordinates e riskLevel.
     *
     * @param r Objeto RiskArea contendo id e novos valores.
     * @return o objeto existente atualizado, ou null se não encontrado.
     */
    @Override
    public RiskArea atualizar(RiskArea r) {
        RiskArea existente = buscarPorId(r.getId());
        if (existente == null) {
            return null;
        }

        // Atualiza nome
        existente.setNome(r.getNome());

        // Atualiza coordenadas
        existente.setCoordinates(r.getCoordinates());

        // Atualiza nível de risco
        existente.setRiskLevel(r.getRiskLevel());

        return existente;
    }

    /**
     * Associa um sensor a esta área de risco:
     * - Busca a área pelo ID fornecido.
     * - Se a área ou o sensor for inválido, lança IllegalArgumentException.
     * - Adiciona o sensor à lista interna de sensores da área
     *   e ajusta a referência bidirecional via addSensor().
     *
     * @param areaId ID da área de risco.
     * @param sensor Sensor a ser associado.
     */
    public void associarSensor(int areaId, Sensor sensor) {
        RiskArea area = buscarPorId(areaId);
        if (area == null || sensor == null) {
            throw new IllegalArgumentException("Área ou Sensor inválido.");
        }
        area.addSensor(sensor);
    }
}
