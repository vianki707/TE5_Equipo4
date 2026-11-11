/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import java.util.List;
import modelo.Plato;

public class MenuCsvAdapter extends FuenteDatos<Plato> {
    @Override
    protected List<Plato> convertir(String txt) {
        return txt.lines().skip(1) // Salta la cabecera del CSV
            .map(l -> l.split(";", -1)) // Separa por punto y coma
            .map(c -> new Plato(
                Integer.parseInt(c[0]), // id
                c[1],                   // nombre
                c[2],                   // categoria
                Double.parseDouble(c[3].replace(',', '.')) // precio
            ))
            .toList();
    }
}