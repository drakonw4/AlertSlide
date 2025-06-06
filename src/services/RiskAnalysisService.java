package services;

import entities.RiskArea;
import entities.RiskLevel;
import entities.Sensor;

import java.util.List;

/**
 * Serviço responsável por analisar sensores e calcular níveis de risco.
 * Utiliza SensorService e RiskAreaService para obter dados cadastrados.
 */
public class RiskAnalysisService {

    private final SensorService sensorService;

    /**
     * Constrói o RiskAnalysisService com dependência de SensorService.
     *
     * @param sensorService o serviço de sensores para obter leituras reais.
     */
    public RiskAnalysisService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Calcula o nível de risco baseado em valores de umidade, pressão e inclinação.
     *
     * @param umidade    valor de umidade (%) obtido do sensor de umidade.
     * @param pressao    valor de pressão (hPa) obtido do sensor de pressão.
     * @param inclinacao valor de inclinação (°) obtido do sensor de inclinação.
     * @return RiskLevel correspondente (ALTO, MODERADO ou BAIXO).
     */
    public RiskLevel calcularNivelRisco(double umidade, double pressao, double inclinacao) {
        int score = 0;

        if (umidade > 80) score += 1;
        if (pressao < 950) score += 1;
        if (inclinacao > 45) score += 2;

        if (score >= 3)      return RiskLevel.ALTO;
        else if (score == 2) return RiskLevel.MODERADO;
        else                 return RiskLevel.BAIXO;
    }

    /**
     * Gera um relatório de risco para uma área específica.
     * Percorre os sensores associados à área, coleta leituras de umidade, pressão e inclinação,
     * calcula o nível de risco e retorna texto formatado.
     *
     * @param areaService o serviço de áreas de risco para obter a lista de áreas.
     * @param areaId      ID da área de risco a ser analisada.
     * @return String contendo o nível de risco e valores de cada sensor.
     */
    public String gerarRelatorioParaArea(RiskAreaService areaService, int areaId) {
        RiskArea area = areaService.buscarPorId(areaId);
        if (area == null) {
            throw new IllegalArgumentException("Área de risco com ID " + areaId + " não encontrada.");
        }

        return gerarRelatorio(area);
    }

    /**
     * Gera um relatório de risco para a área informada.
     * Se algum tipo de sensor estiver faltando, utiliza simulação pontual.
     *
     * @param area RiskArea cujos sensores serão analisados.
     * @return String contendo nível de risco e leituras.
     */
    public String gerarRelatorio(RiskArea area) {
        // Obtém todos os sensores cadastrados
        List<Sensor> todosSensores = sensorService.listar(); // herdado de AbstractCrudService

        double umidade    = Double.NaN;
        double pressao    = Double.NaN;
        double inclinacao = Double.NaN;

        for (Sensor s : todosSensores) {
            if (s.getRiskArea() != null && s.getRiskArea().getId() == area.getId()) {
                switch (s.getType()) {
                    case UMIDADE    -> umidade = s.getLeituraAtual();
                    case PRESSAO    -> pressao = s.getLeituraAtual();
                    case INCLINACAO -> inclinacao = s.getLeituraAtual();
                }
            }
        }

        // Se algum valor estiver ausente, faz fallback para simulação:
        if (Double.isNaN(umidade))    umidade    = sensorService.gerarUmidade();
        if (Double.isNaN(pressao))    pressao    = sensorService.gerarPressao();
        if (Double.isNaN(inclinacao)) inclinacao = sensorService.gerarInclinacao();

        RiskLevel nivel = calcularNivelRisco(umidade, pressao, inclinacao);
        return String.format(
                "Relatório Área [%s]:\n" +
                        "- Umidade: %.2f %%\n" +
                        "- Pressão: %.2f hPa\n" +
                        "- Inclinação: %.2f °\n" +
                        "=> Nível de Risco: %s",
                area.getNome(), umidade, pressao, inclinacao, nivel
        );
    }

    /**
     * Analisa e imprime relatório de risco para todas as áreas cadastradas.
     *
     * @param areaService o serviço de áreas de risco para obter a lista de áreas.
     */
    public void analisarTodasAreas(RiskAreaService areaService) {
        List<RiskArea> areas = areaService.listar(); // herdado de AbstractCrudService
        System.out.println("\n🔍 Iniciando análise de risco para todas as áreas...");
        for (RiskArea area : areas) {
            String rel = gerarRelatorio(area);
            System.out.println("\n--- " + area.getNome() + " ---");
            System.out.println(rel);
        }
        System.out.println();
    }
}
