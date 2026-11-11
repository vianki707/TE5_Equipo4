package filtros;

import java.util.List;
import modelo.LineaPedido;

/** Un filtro aplica UNA regla de limpieza y devuelve las líneas que sí la cumplen. */
public interface Filtro {

    /** Nombre legible de la regla; el Pipeline lo usa en el informe de descartes. */
    String nombre();

    /** Recibe las líneas y devuelve la misma lista sin las que no cumplen la regla. */
    List<LineaPedido> aplicar(List<LineaPedido> lineas);
}
