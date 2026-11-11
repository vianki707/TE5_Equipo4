package filtros;

import java.util.List;
import modelo.LineaPedido;

/**
 * Regla 2: solo cuentan como venta los pedidos que pasaron por el estado PAGADO.
 * Es una regla de PEDIDO: todas las líneas de un pedido comparten el mismo historial,
 * así que se conservan todas o se descartan todas.
 */
public class FiltroSoloPagados implements Filtro {

    @Override
    public String nombre() {
        return "Pedidos que no fueron pagados";
    }

    @Override
    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream()
                .filter(linea -> linea.historial().stream()
                        .anyMatch(cambio -> cambio.estado().equals("PAGADO")))
                .toList();
    }
}
