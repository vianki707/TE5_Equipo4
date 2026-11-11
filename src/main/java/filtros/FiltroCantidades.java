package filtros;

import java.util.List;
import modelo.LineaPedido;

/**
 * Regla 4: la cantidad de cada plato debe ser un entero mayor que cero.
 * Es una regla de LÍNEA: solo se descarta la línea inválida, no el pedido entero.
 * (La cantidad ya es int, así que "entero" lo garantiza el tipo; aquí se valida "> 0".)
 */
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
