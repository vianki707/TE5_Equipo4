/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package principal;

import adaptadores.FuenteDatos;
import fabrica.FabricaFuentes;
import java.nio.file.Path;
import java.util.List;

public class SistemaPedidos {

    public static void main(String[] args) {
        // 1. Instanciamos la fábrica
        FabricaFuentes fabrica = new FabricaFuentes();
        
        try {
            // 2. Le pedimos a la fábrica el adaptador para el menú. 
            // (La ruta ficticia es "menu.csv", debes crear un archivo real luego para probar de verdad)
            FuenteDatos<?> adaptadorMenu = fabrica.crear("menu_csv", Path.of("menu.csv"));
            System.out.println("Adaptador de menú creado correctamente: " + adaptadorMenu.getClass().getSimpleName());
            
            // 3. Le pedimos el adaptador de la pasarela de pagos
            FuenteDatos<?> adaptadorPasarela = fabrica.crear("pasarela_csv", Path.of("pagos.csv"));
            System.out.println("Adaptador de pasarela creado correctamente: " + adaptadorPasarela.getClass().getSimpleName());
            // 4. Le pedimos el adaptador JSON
            FuenteDatos<?> adaptadorJson = fabrica.crear("app_json", Path.of("pedidos.json"));
            System.out.println("Adaptador de JSON creado correctamente: " + adaptadorJson.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
