package filtros;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modelo.LineaPedido;

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
            if (vistas.add(clave)) {      
                resultado.add(linea);
            }
        }
        return resultado;
    }
}
