package principal;

import adaptadores.FuenteDatos;
import fabrica.FabricaFuentes;
import modelo.Plato;
import modelo.Pago;
import modelo.LineaPedido;
import java.nio.file.Path;
import java.util.List;

public class SistemaPedidos {
    public static void main(String[] args) {
        FabricaFuentes fabrica = new FabricaFuentes();
        
        try {
            // 1. Probar Menú
            FuenteDatos<?> adaptadorMenu = fabrica.crear("menu_csv", Path.of("menu.csv"));
            // Hacemos el cast seguro porque sabemos qué tipo devuelve
            List<Plato> platos = (List<Plato>) adaptadorMenu.leer(Path.of("menu.csv"));
            System.out.println("Platos leídos: " + platos.size());
            platos.forEach(System.out::println);
            
            // 2. Probar Pasarela
            FuenteDatos<?> adaptadorPasarela = fabrica.crear("pasarela_csv", Path.of("pagos.csv"));
            List<Pago> pagos = (List<Pago>) adaptadorPasarela.leer(Path.of("pagos.csv"));
            System.out.println("\nPagos leídos: " + pagos.size());
            pagos.forEach(System.out::println);
            
            // 3. Probar JSON
            FuenteDatos<?> adaptadorJson = fabrica.crear("app_json", Path.of("pedidos.json"));
            List<LineaPedido> lineas = (List<LineaPedido>) adaptadorJson.leer(Path.of("pedidos.json"));
            System.out.println("\nLíneas de pedido leídas: " + lineas.size());
            lineas.forEach(System.out::println);
            
        } catch (Exception e) {
            System.out.println("Error en la ejecución: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
