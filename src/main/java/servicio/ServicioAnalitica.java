package servicio;

import adaptadores.AppPedidosJsonAdapter;
import adaptadores.FuenteDatos;
import adaptadores.MenuCsvAdapter;
import adaptadores.PasarelaCsvAdapter;
import fabrica.ConfigFuente;
import fabrica.FabricaFuentes;
import filtros.Filtro;
import filtros.FiltroCantidades;
import filtros.FiltroDuplicados;
import filtros.FiltroPagoAprobado;
import filtros.FiltroSoloPagados;
import filtros.Pipeline;
import filtros.ResultadoPipeline;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import metricas.Metrica;
import modelo.LineaPedido;
import modelo.Pago;
import modelo.Plato;
import repositorio.RepoAnalitica;

/**
 * Patrón FACADE: solo dos métodos públicos, cargar() y resumen(desde, hasta).
 */
public class ServicioAnalitica {
    private static final Path CONFIG = Path.of("config/fuentes.json");

    private final RepoAnalitica repo;
    private final List<Metrica> metricas;

    public ServicioAnalitica(RepoAnalitica repo, List<Metrica> metricas) {
        this.repo = repo;
        this.metricas = metricas;
    }

    /** Lee las fuentes (Adapter + Factory), limpia (Pipeline) y guarda en el repositorio. */
    public void cargar() {
        repo.limpiar();
        FabricaFuentes fabrica = new FabricaFuentes();
        List<LineaPedido> lineas = null;
        List<Pago> pagos = null;
        List<Plato> menu = null;

        for (ConfigFuente config : ConfigFuente.cargar(CONFIG)) {
            FuenteDatos<?> fuente = fabrica.crear(config);
            if (fuente instanceof AppPedidosJsonAdapter app) {
                lineas = app.leer();
            } else if (fuente instanceof PasarelaCsvAdapter pasarela) {
                pagos = pasarela.leer();
            } else if (fuente instanceof MenuCsvAdapter menuCsv) {
                menu = menuCsv.leer();
            }
        }
        if (lineas == null || pagos == null || menu == null) {
            throw new IllegalStateException("Faltan fuentes en " + CONFIG);
        }
        System.out.println("Leidos: " + lineas.size() + " lineas, " + pagos.size()
                + " pagos, " + menu.size() + " platos\n");

        List<Filtro> filtros = List.of(
                new FiltroDuplicados(),
                new FiltroCantidades(),
                new FiltroSoloPagados(),
                new FiltroPagoAprobado(pagos));
        ResultadoPipeline resultado = new Pipeline(filtros).ejecutar(lineas);
        System.out.println(resultado.informe() + "\n");

        repo.guardarLineas(resultado.lineas());
        repo.guardarPagos(pagos);
    }

    public void resumen(LocalDate desde, LocalDate hasta) {
        List<LineaPedido> lineas = repo.lineasEntre(desde, hasta);
        System.out.println("=== Resumen del " + desde + " al " + hasta + " ===");
        for (Metrica m : metricas) {
            System.out.println("--- " + m.nombre() + " ---");
            System.out.println(m.calcular(lineas));
        }
    }
}
