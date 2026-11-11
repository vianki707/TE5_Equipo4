package fabrica;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Una entrada de config/fuentes.json: qué tipo de fuente es y dónde está su archivo. */
public record ConfigFuente(String tipo, String ruta) {

    /** Lee config/fuentes.json y devuelve la lista de fuentes configuradas. */
    public static List<ConfigFuente> cargar(Path archivoConfig) {
        try {
            String texto = Files.readString(archivoConfig);
            JsonObject raiz = JsonParser.parseString(texto).getAsJsonObject();
            List<ConfigFuente> fuentes = new ArrayList<>();
            for (JsonElement e : raiz.getAsJsonArray("fuentes")) {
                JsonObject f = e.getAsJsonObject();
                fuentes.add(new ConfigFuente(f.get("tipo").getAsString(), f.get("ruta").getAsString()));
            }
            return fuentes;
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer la configuración: " + archivoConfig, e);
        }
    }
}
