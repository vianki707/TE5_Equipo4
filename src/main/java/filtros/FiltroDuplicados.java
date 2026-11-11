package filtros;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modelo.LineaPedido;

/**
 * Regla 5: cada pedido tiene un número único y no puede contarse dos veces.
 * Se conserva la primera aparición de cada línea (pedido + número de línea)
 * y se descartan las repetidas.
 */
public class FiltroDuplicados implements Filtro {

    @Override
    public String nombre() {
        return "Pedidos repetidos";
    }

    @Override
    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        Set<String> vistas = new HashSet<>();
        List<LineaPedido> resultado = new ArrayList<>();
        for (LineaPedido linea : lineas) {
            String clave = linea.numeroPedido() + "#" + linea.numeroLinea();
            if (vistas.add(clave)) {      // add() devuelve false si ya estaba
                resultado.add(linea);
            }
        }
        return resultado;
    }
}
