/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import java.time.LocalDateTime;
import java.util.List;
import modelo.Pago; // Importamos el modelo Pago

public class PasarelaCsvAdapter extends FuenteDatos<Pago> {
    @Override
    protected List<Pago> convertir(String txt) {
        return txt.lines().skip(1) // Salta la cabecera
            .map(l -> l.split(";", -1))
            .map(c -> new Pago(
                c[0], 
                Double.parseDouble(c[1].replace(',', '.')), 
                c[2], c[3], c[4], 
                LocalDateTime.parse(c[5])
            ))
            .toList();
    }
}
