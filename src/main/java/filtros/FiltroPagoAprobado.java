package filtros;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import modelo.LineaPedido;
import modelo.Pago;

public class FiltroPagoAprobado implements Filtro {
    private static final double TOLERANCIA = 0.005; 
    private final Map<String, List<Pago>> pagosPorPedido;
    public FiltroPagoAprobado(List<Pago> pagos) {
        this.pagosPorPedido = pagos.stream()
                .collect(Collectors.groupingBy(Pago::referencia));
    }

    @Override
    public String nombre() {
        return "Pago no aprobado";
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
