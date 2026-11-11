package fabrica;

import adaptadores.FuenteDatos;
import adaptadores.PasarelaCsvAdapter;
import adaptadores.MenuCsvAdapter;
import adaptadores.AppPedidosJsonAdapter; // <-- Importación del nuevo adaptador
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FabricaFuentes {
    private final Map<String, Function<Path, FuenteDatos<?>>> tipos = new HashMap<>();

    // Este es el ÚNICO constructor
    public FabricaFuentes() {
        registrar("pasarela_csv", base -> new PasarelaCsvAdapter());
        registrar("menu_csv", base -> new MenuCsvAdapter());
        registrar("app_json", base -> new AppPedidosJsonAdapter()); // <-- Registro del JSON
    }

    public void registrar(String tipo, Function<Path, FuenteDatos<?>> constructor) {
        tipos.put(tipo, constructor);
    }

    public FuenteDatos<?> crear(String tipo, Path base) {
        var constructor = tipos.get(tipo);
        if (constructor == null) {
            throw new IllegalArgumentException("Tipo de fuente no soportado: " + tipo);
        }
        return constructor.apply(base);
    }
}