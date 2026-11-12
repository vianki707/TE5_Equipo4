package principal;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import metricas.Metrica;
import metricas.PedidosPorHora;
import metricas.PlatosTop;
import metricas.TicketPromedio;
import repositorio.RepoAnalitica;
import repositorio.RepoAnaliticaMemoria;
import servicio.ServicioAnalitica;

public class Main {
    public static void main(String[] args) {
        RepoAnalitica repo = new RepoAnaliticaMemoria();

        Map<Integer, String> catalogoPlatos = new HashMap<>();
        List<Metrica> metricas = List.of(
                new PlatosTop(catalogoPlatos),
                new PedidosPorHora(),
                new TicketPromedio());

        ServicioAnalitica servicio = new ServicioAnalitica(repo, metricas, catalogoPlatos);
        servicio.cargar();
        servicio.resumen(LocalDate.of(2026, 10, 19), LocalDate.of(2026, 10, 30));
    }
}
