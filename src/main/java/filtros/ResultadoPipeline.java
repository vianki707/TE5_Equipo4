package filtros;

import java.util.List;
import modelo.LineaPedido;

/** Resultado de ejecutar el Pipeline: las líneas limpias y el informe de descartes. */
public record ResultadoPipeline(int lineasLeidas, List<LineaPedido> lineas, List<Descarte> descartes) {

    /** Número de pedidos distintos que quedan (las líneas se agrupan por numeroPedido). */
    public int cantidadPedidos() {
        return (int) lineas.stream().map(LineaPedido::numeroPedido).distinct().count();
    }

    public String informe() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lineas leidas: ").append(lineasLeidas).append('\n');
        for (Descarte d : descartes) {
            sb.append(" - ").append(d.filtro()).append(": ").append(d.lineas()).append(" lineas descartadas\n");
        }
        sb.append("Quedan al final: ").append(lineas.size()).append(" lineas de ")
          .append(cantidadPedidos()).append(" pedidos");
        return sb.toString();
    }
}
