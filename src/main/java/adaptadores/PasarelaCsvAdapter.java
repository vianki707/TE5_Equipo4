package adaptadores;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.Pago;

/**
 * Lee pagos_pasarela.csv: separado por ';', montos con coma decimal (7,50)
 * y fechas con formato dd/MM/yyyy HH:mm:ss.
 */
public class PasarelaCsvAdapter extends FuenteDatos<Pago> {
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public PasarelaCsvAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    protected List<Pago> convertir(String contenido) {
        return contenido.lines()
                .skip(1)                          // cabecera
                .filter(l -> !l.isBlank())        // ignora líneas vacías
                .map(l -> l.split(";", -1))       // -1 conserva las columnas vacías
                .map(c -> new Pago(
                        c[0].trim(),
                        Double.parseDouble(c[1].trim().replace(',', '.')),
                        c[2].trim(),
                        c[3].trim(),
                        c[4].trim(),
                        LocalDateTime.parse(c[5].trim(), FORMATO_FECHA)))
                .toList();
    }
}
