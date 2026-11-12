package filtros;

import java.util.List;
import modelo.LineaPedido;

public class FiltroSoloPagados implements Filtro {
    @Override
    public String nombre() {
        return "Pedidos no pagados";
    }

    @Override
    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream()
                .filter(linea -> linea.historial().stream()
                        .anyMatch(cambio -> cambio.estado().equals("PAGADO")))
                .toList();
    }
}
