/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class FuenteDatos<T> {
    protected abstract List<T> convertir(String txt);

    public final List<T> leer(Path ruta) {
        try {
            String contenido = Files.readString(ruta);
            return convertir(contenido);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo: " + ruta, e);
        }
    }
}