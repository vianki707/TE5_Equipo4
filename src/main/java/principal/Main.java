package principal;

import java.time.LocalDate;
import java.util.List;
import metricas.Metrica;
import metricas.ResumenBasico;
import repositorio.RepoAnalitica;
import repositorio.RepoAnaliticaMemoria;
import servicio.ServicioAnalitica;

public class Main {
    public static void main(String[] args) {
        RepoAnalitica repo = new RepoAnaliticaMemoria();
        // TODO (integrante 3): cambiar por PlatosTop, PedidosPorHora y la tercera métrica
        List<Metrica> metricas = List.of(new ResumenBasico());

        ServicioAnalitica servicio = new ServicioAnalitica(repo, metricas);
        servicio.cargar();
        servicio.resumen(LocalDate.of(2026, 10, 19), LocalDate.of(2026, 10, 30));
    }
}
