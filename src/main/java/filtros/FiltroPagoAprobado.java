package filtros;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import modelo.LineaPedido;
import modelo.Pago;

/**
 * Regla 3: un pedido pagado debe tener un pago APROBADO en la pasarela
 * y el monto cobrado debe ser igual al total del pedido.
 * Es una regla de PEDIDO: si el pedido no cumple, se descartan todas sus líneas.
 *
 * Un pedido puede tener varios intentos de pago (RECHAZADA y luego APROBADA),
 * por eso se agrupan por referencia y se busca si ALGUNO cumple.
 */
public class FiltroPagoAprobado implements Filtro {
    private static final double TOLERANCIA = 0.005;  // medio centavo: evita errores de double

    private final Map<String, List<Pago>> pagosPorPedido;

    public FiltroPagoAprobado(List<Pago> pagos) {
        this.pagosPorPedido = pagos.stream()
                .collect(Collectors.groupingBy(Pago::referencia));
    }

    @Override
    public String nombre() {
        return "Pago no aprobado o monto distinto al total";
    }

    @Override
    public List<LineaPedido> aplicar(List<LineaPedido> lineas) {
        return lineas.stream()
                .filter(this::tienePagoValido)
                .toList();
    }

    private boolean tienePagoValido(LineaPedido linea) {
        List<Pago> intentos = pagosPorPedido.getOrDefault(linea.numeroPedido(), List.of());
        return intentos.stream().anyMatch(pago ->
                pago.estado().equals("APROBADA")
                && Math.abs(pago.monto() - linea.totalPedido()) < TOLERANCIA);
    }
}
