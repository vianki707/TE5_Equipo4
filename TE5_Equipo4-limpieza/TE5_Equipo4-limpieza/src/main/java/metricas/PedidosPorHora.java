package metricas;

import modelo.LineaPedido;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PedidosPorHora implements Metrica {

    @Override
    public String nombre() {
        return "Pedidos por Hora (Pico)";
    }

    @Override
    public String calcular(List<LineaPedido> datos) {
        // 1. Obtenemos un pedido único por cada línea (para no contar varias veces el mismo pedido)
        List<LineaPedido> pedidosUnicos = datos.stream()
            .collect(Collectors.toMap(
                LineaPedido::numeroPedido,
                linea -> linea,
                (existente, nuevo) -> existente // Si se repite, conservamos el primero
            ))
            .values().stream().toList();

        // 2. Agrupamos por la hora del estado "PAGADO" en el historial
        Map<Integer, Long> pedidosPorHora = pedidosUnicos.stream()
            .filter(pedido -> pedido.historial().stream().anyMatch(c -> c.estado().equals("PAGADO")))
            .collect(Collectors.groupingBy(
                pedido -> pedido.historial().stream()
                    .filter(c -> c.estado().equals("PAGADO"))
                    .findFirst().get()
                    .fechaHora().getHour(),
                Collectors.counting()
            ));

        // 3. Mostramos las 3 horas con más pedidos
        StringBuilder sb = new StringBuilder();
        pedidosPorHora.entrySet().stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .limit(3)
            .forEach(entry -> sb.append("   - Hora ").append(entry.getKey()).append(":00 -> ")
                               .append(entry.getValue()).append(" pedidos\n"));

        return sb.toString();
    }
}