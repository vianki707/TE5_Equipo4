package metricas;

import modelo.LineaPedido;
import java.util.List;

public class ReporteEstadisticas {
    private List<Metrica> metricas;

    public ReporteEstadisticas(List<Metrica> metricas) {
        this.metricas = metricas;
    }

    public void generarReporte(List<LineaPedido> datosLimpios) {
        System.out.println("Reporte de Estadisticas: ");
        for (Metrica metrica : metricas) {
            System.out.println("\n>> " + metrica.nombre());
            System.out.println(metrica.calcular(datosLimpios));
        }
    }
}
