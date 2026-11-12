package metricas;

import modelo.LineaPedido;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlatosTop implements Metrica {
    private final Map<Integer, String> mapaPlatos;

    // Recibimos el mapa de platos en el constructor
    public PlatosTop(Map<Integer, String> mapaPlatos) {
        this.mapaPlatos = mapaPlatos;
    }

    @Override
    public String nombre() {
        return "Top 5 Platos Más Vendidos";
    }

    @Override
    public String calcular(List<LineaPedido> datos) {
        Map<Integer, Integer> ventasPorPlato = datos.stream()
            .collect(Collectors.groupingBy(
                LineaPedido::platoId, 
                Collectors.summingInt(LineaPedido::cantidad)
            ));

        StringBuilder sb = new StringBuilder();
        ventasPorPlato.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> {
                String nombrePlato = mapaPlatos.getOrDefault(entry.getKey(), "Plato ID " + entry.getKey());
                sb.append("   - ").append(nombrePlato).append(": ").append(entry.getValue()).append(" unidades\n");
            });

        return sb.toString();
    }
}