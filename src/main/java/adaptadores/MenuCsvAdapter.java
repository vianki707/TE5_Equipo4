package adaptadores;

import java.util.List;
import modelo.Plato;

public class MenuCsvAdapter extends FuenteDatos<Plato> {
    @Override
    protected List<Plato> convertir(String txt) {
        return txt.lines().skip(1) 
            .map(l -> l.split(",", -1)) // <-- AHORA SEPARA POR COMA
            .map(c -> new Plato(
                Integer.parseInt(c[0]), // id
                c[1],                   // nombre
                c[2],                   // categoria
                Double.parseDouble(c[3]) // precio (tu archivo ya usa punto para decimales como 3.75)
            ))
            .toList();
    }
}