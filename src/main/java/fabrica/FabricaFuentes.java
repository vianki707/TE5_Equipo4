package fabrica;

import adaptadores.AppPedidosJsonAdapter;
import adaptadores.FuenteDatos;
import adaptadores.MenuCsvAdapter;
import adaptadores.PasarelaCsvAdapter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/** Factory: elige el adaptador según el campo "tipo" de fuentes.json, sin cadenas de if. */
public class FabricaFuentes {
    private final Map<String, Function<Path, FuenteDatos<?>>> tipos = new HashMap<>();

    public FabricaFuentes() {
        registrar("app_json", AppPedidosJsonAdapter::new);
        registrar("pasarela_csv", PasarelaCsvAdapter::new);
        registrar("menu_csv", MenuCsvAdapter::new);
    }

    /** Permite agregar un tipo nuevo sin tocar el resto del programa. */
    public void registrar(String tipo, Function<Path, FuenteDatos<?>> constructor) {
        tipos.put(tipo, constructor);
    }

    public FuenteDatos<?> crear(ConfigFuente config) {
        Function<Path, FuenteDatos<?>> constructor = tipos.get(config.tipo());
        if (constructor == null) {
            throw new IllegalArgumentException("Tipo de fuente no soportado: " + config.tipo());
        }
        return constructor.apply(Path.of(config.ruta()));
    }
}
