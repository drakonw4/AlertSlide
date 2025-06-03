package services;

public class AlertService {

    public void emitirAlerta(String nivelRisco) {
        switch (nivelRisco) {
            case "ALTO":
                System.out.println("🚨 ALERTA VERMELHO: Risco alto de deslizamento!");
                break;
            case "MODERADO":
                System.out.println("⚠️ ALERTA LARANJA: Monitoramento contínuo necessário.");
                break;
            case "BAIXO":
                System.out.println("✅ Alerta verde: Condições estáveis.");
                break;
            default:
                System.out.println("Nível de risco desconhecido.");
        }
    }
}
