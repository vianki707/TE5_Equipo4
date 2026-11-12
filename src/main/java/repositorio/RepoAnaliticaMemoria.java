package repositorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.LineaPedido;
import modelo.Pago;

/** Versión en memoria del repositorio. */
public class RepoAnaliticaMemoria implements RepoAnalitica {
    private final List<LineaPedido> lineas = new ArrayList<>();
    private final List<Pago> pagos = new ArrayList<>();

    @Override
    public void guardarLineas(List<LineaPedido> nuevas) { lineas.addAll(nuevas); }

    @Override
    public void guardarPagos(List<Pago> nuevos) { pagos.addAll(nuevos); }

    @Override
    public List<LineaPedido> lineasEntre(LocalDate desde, LocalDate hasta) {
        // usa registradoEn, la fecha del pedido
        return lineas.stream()
                .filter(l -> enRango(l.registradoEn().toLocalDate(), desde, hasta))
                .toList();
    }

    @Override
    public List<Pago> pagosEntre(LocalDate desde, LocalDate hasta) {
        return pagos.stream()
                .filter(p -> enRango(p.fechaHora().toLocalDate(), desde, hasta))
                .toList();
    }

    @Override
    public void limpiar() { lineas.clear(); pagos.clear(); }

    private boolean enRango(LocalDate f, LocalDate desde, LocalDate hasta) {
        return !f.isBefore(desde) && !f.isAfter(hasta);
    }
}
