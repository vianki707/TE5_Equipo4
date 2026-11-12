package metricas;

import modelo.LineaPedido;
import java.util.List;

public interface Metrica {
    // Nombre de la métrica para mostrarla en el reporte
    String nombre();
    
    // Método que calcula la estadística
    // Recibe las líneas de pedido ya limpias (Filtradas)
    // Retorna un String con el resultado formateado para imprimir
    String calcular(List<LineaPedido> datos);
}