package filtros;

import java.util.ArrayList;
import java.util.List;
import modelo.LineaPedido;

public class Pipeline {
    private final List<Filtro> filtros;
    public Pipeline(List<Filtro> filtros) {
        this.filtros = List.copyOf(filtros);
    }

    public ResultadoPipeline ejecutar(List<LineaPedido> entrada) {
        List<LineaPedido> actual = entrada;
        List<Descarte> descartes = new ArrayList<>();
        for (Filtro filtro : filtros) {
            int antes = actual.size();
            actual = filtro.aplicar(actual);
            descartes.add(new Descarte(filtro.nombre(), antes - actual.size()));
        }
        return new ResultadoPipeline(entrada.size(), actual, descartes);
    }
}
