package filtros;

import java.util.List;
import modelo.LineaPedido;

public interface Filtro {
    String nombre();
    List<LineaPedido> aplicar(List<LineaPedido> lineas);
}
