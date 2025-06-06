package services;

import common.AbstractCrudService;
import entities.Alert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço genérico de CRUD para Alert.
 * - Gerencia lista de Alert e controle de IDs via AbstractCrudService.
 * - Inclui método extra para buscar alertas por área de risco.
 */
public class AlertService extends AbstractCrudService<Alert> {

    @Override
    protected int getId(Alert a) {
        return a.getId();
    }

    @Override
    protected void setId(Alert a, int id) {
        a.setId(id);
    }

    /**
     * Atualiza um alerta existente:
     * - Permite alterar apenas a área ou o nível de risco. Não altera mensagem nem timestamp.
     *
     * @param a Objeto Alert contendo id e novos valores para area e/ou riskLevel.
     * @return o objeto existente atualizado, ou null se não encontrado.
     */
    @Override
    public Alert atualizar(Alert a) {
        Alert existente = buscarPorId(a.getId());
        if (existente == null) {
            return null;
        }

        // Atualiza área de risco
        existente.setArea(a.getArea());

        // Atualiza nível de risco
        existente.setRiskLevel(a.getRiskLevel());

        return existente;
    }

    /**
     * Retorna todos os alertas gerados para uma área específica.
     *
     * @param areaId ID da área de risco.
     * @return lista de Alert cujo campo `area.id == areaId`.
     */
    public List<Alert> buscarPorArea(int areaId) {
        return itens.stream()
                .filter(alert -> alert.getArea().getId() == areaId)
                .collect(Collectors.toList());
    }
}
