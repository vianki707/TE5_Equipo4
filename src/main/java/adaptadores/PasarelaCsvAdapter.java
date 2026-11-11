package adaptadores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.Pago; 

public class PasarelaCsvAdapter extends FuenteDatos<Pago> {
    @Override
    protected List<Pago> convertir(String txt) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return txt.lines().skip(1) 
            .map(l -> l.split(";", -1)) // <-- 1. Volvemos a separar las columnas por PUNTO Y COMA
            .map(c -> new Pago(
                c[0], 
                Double.parseDouble(c[1].replace(',', '.')), // <-- 2. Cambiamos la coma por punto para los decimales (ej. 3,75 a 3.75)
                c[2], 
                c[3], 
                c[4], 
                LocalDateTime.parse(c[5], formatoFecha)
            ))
            .toList();
    }
}