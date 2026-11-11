package principal;

import adaptadores.AppPedidosJsonAdapter;
import adaptadores.FuenteDatos;
import adaptadores.MenuCsvAdapter;
import adaptadores.PasarelaCsvAdapter;
import fabrica.ConfigFuente;
import fabrica.FabricaFuentes;
import filtros.Descarte;
import filtros.Filtro;
import filtros.FiltroCantidades;
import filtros.FiltroDuplicados;
import filtros.FiltroPagoAprobado;
import filtros.FiltroSoloPagados;
import filtros.Pipeline;
import filtros.ResultadoPipeline;
import java.nio.file.Path;
import java.util.List;
import modelo.LineaPedido;
import modelo.Pago;
import modelo.Plato;

/**
 * MAIN TEMPORAL para probar Adapter + Factory + Pipeline con los datos reales.
 * El integrante 4 lo reemplazará por un main que use solo ServicioAnalitica.
 */
public class SistemaPedidos {

    public static void main(String[] args) {
        FabricaFuentes fabrica = new FabricaFuentes();
        List<LineaPedido> lineas = null;
        List<Pago> pagos = null;
        List<Plato> menu = null;

        for (ConfigFuente config : ConfigFuente.cargar(Path.of("config/fuentes.json"))) {
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
        List<Filtro> filtros = List.of(
                new FiltroDuplicados(),
                new FiltroCantidades(),
                new FiltroSoloPagados(),
                new FiltroPagoAprobado(pagos));
        ResultadoPipeline resultado = new Pipeline(filtros).ejecutar(lineas);
        System.out.println(resultado.informe());
    }
}
