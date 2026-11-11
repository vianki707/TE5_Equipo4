package filtros;

import java.util.List;
import modelo.LineaPedido;

public class FiltroCantidades implements Filtro {
    @Override
    public String nombre() {
        return "Cantidad invalida (cero o negativa)";
    }

    @Override
    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream()
                .filter(linea -> linea.cantidad() > 0)
                .toList();
    }
}
