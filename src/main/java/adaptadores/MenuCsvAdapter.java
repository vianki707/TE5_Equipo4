package adaptadores;

import java.nio.file.Path;
import java.util.List;
import modelo.Plato;

/** Lee menu_admin.csv: separado por coma, con precio de punto decimal. */
public class MenuCsvAdapter extends FuenteDatos<Plato> {

    public MenuCsvAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    protected List<Plato> convertir(String contenido) {
        return contenido.lines()
                .skip(1)                          // cabecera
                .filter(l -> !l.isBlank())
                .map(l -> l.split(",", -1))
                .map(c -> new Plato(
                        Integer.parseInt(c[0].trim()),
                        c[1].trim(),
                        c[2].trim(),
                        Double.parseDouble(c[3].trim()),
                        c[4].trim()))
                .toList();
    }
}
