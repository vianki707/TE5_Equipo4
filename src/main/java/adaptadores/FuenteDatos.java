package adaptadores;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Interfaz común (Adapter) para las tres fuentes. Cada adaptador conoce su archivo
 * y su formato; el resto del programa solo llama a leer().
 */
public abstract class FuenteDatos<T> {
    private final Path ruta;

    protected FuenteDatos(Path ruta) {
        this.ruta = ruta;
    }

    /** Traduce el contenido del archivo (texto) a objetos del modelo. */
    protected abstract List<T> convertir(String contenido);

    public final List<T> leer() {
        String contenido;
        try {
            contenido = Files.readString(ruta);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer el archivo: " + ruta, e);
        }
        try {
            return convertir(contenido);
        } catch (RuntimeException e) {
            throw new IllegalStateException("Formato inesperado en " + ruta + ": " + e, e);
        }
    }
}
