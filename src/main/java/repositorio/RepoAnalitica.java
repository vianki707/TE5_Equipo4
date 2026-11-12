package repositorio;


import java.time.LocalDate;
import java.util.List;
import modelo.LineaPedido;
import modelo.Pago;

/** Patrón REPOSITORY */
public interface RepoAnalitica {
    void guardarLineas(List<LineaPedido> lineas);
    void guardarPagos(List<Pago> pagos);
    List<LineaPedido> lineasEntre(LocalDate desde, LocalDate hasta);
    List<Pago> pagosEntre(LocalDate desde, LocalDate hasta);
    void limpiar();
}
