package principal;

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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import modelo.LineaPedido;
import modelo.Pago;
import modelo.Plato;

// Imports para las métricas (Tarea 4)
import metricas.Metrica;
import metricas.PlatosTop;
import metricas.PedidosPorHora;
import metricas.TicketPromedio;
import metricas.ReporteEstadisticas;
import java.util.ArrayList;

public class SistemaPedidos {
    
    public static void main(String[] args) {
        FabricaFuentes fabrica = new FabricaFuentes();
        List<LineaPedido> lineas = null;
        List<Pago> pagos = null;
        List<Plato> menu = null;

        // 1. CARGA DE DATOS (Factory + Adapter)
        for (ConfigFuente config : ConfigFuente.cargar(Path.of("TE5_Equipo4-limpieza/config/fuentes.json"))) {
            FuenteDatos<?> fuente = fabrica.crear(config);
            if (fuente instanceof AppPedidosJsonAdapter app) {
                lineas = app.leer();
            } else if (fuente instanceof PasarelaCsvAdapter pasarela) {
                pagos = pasarela.leer();
            } else if (fuente instanceof MenuCsvAdapter menuCsv) {
                menu = menuCsv.leer();
            }
        }
        System.out.println("Leidos: " + lineas.size() + " lineas, " + pagos.size()
                + " pagos, " + menu.size() + " platos\n");

        // 2. LIMPIEZA DE DATOS (Tarea 3 - Pipeline)
        List<Filtro> filtros = List.of(
                new FiltroDuplicados(),
                new FiltroCantidades(),
                new FiltroSoloPagados(),
                new FiltroPagoAprobado(pagos));
        ResultadoPipeline resultado = new Pipeline(filtros).ejecutar(lineas);
        System.out.println(resultado.informe());
        
        // Obtenemos la lista limpia de líneas de pedido
        List<LineaPedido> datosLimpios = resultado.lineas();

        // --- TAREA 4: calculo de esdtadisticas
        
        System.out.println("\n-Iniciando calculo de metricas: ");

        // 1. Creamos un Mapa de platos para obtener los nombres (id -> nombre).
        //    Esto es necesario porque las métricas (PlatosTop) solo tienen el ID.
        Map<Integer, String> mapaPlatos = menu.stream()
            .collect(Collectors.toMap(Plato::id, Plato::nombre));

        // 2. Creamos las estrategias (métricas) que queremos calcular.
        List<Metrica> misMetricas = new ArrayList<>();
        misMetricas.add(new PlatosTop(mapaPlatos)); // Le pasamos el mapa para que muestre nombres reales
        misMetricas.add(new PedidosPorHora());
        misMetricas.add(new TicketPromedio());

        // 3. Creamos el contexto (ReporteEstadisticas) y lo ejecutamos.
        ReporteEstadisticas reporte = new ReporteEstadisticas(misMetricas);
        reporte.generarReporte(datosLimpios);
    }
}
