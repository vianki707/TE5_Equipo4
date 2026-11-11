package adaptadores;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class FuenteDatos<T> {
    private final Path ruta;
    protected FuenteDatos(Path ruta) {
        this.ruta = ruta;
    }
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
