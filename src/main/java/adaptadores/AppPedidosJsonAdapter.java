package adaptadores;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import modelo.LineaPedido;

public class AppPedidosJsonAdapter extends FuenteDatos<LineaPedido> {
    
    @Override
    protected List<LineaPedido> convertir(String txt) {
        List<LineaPedido> lista = new ArrayList<>();
        
        JsonArray pedidos = JsonParser.parseString(txt).getAsJsonArray();
        
        for (JsonElement elemento : pedidos) {
            JsonObject pedido = elemento.getAsJsonObject();
            
            // 1. Extraemos los datos generales del pedido
            String idPedido = pedido.get("numeroPedido").getAsString(); 
            String estadoPedido = pedido.get("estado").getAsString(); // <-- Leemos el estado de afuera
            
            JsonArray lineas = pedido.getAsJsonArray("lineas");
            
            for (JsonElement lineaElemento : lineas) {
                JsonObject linea = lineaElemento.getAsJsonObject();
                
                // 2. Creamos la línea pasando el estado del pedido general
                lista.add(new LineaPedido(
                    idPedido, 
                    linea.get("platoId").getAsInt(),
                    linea.get("cantidad").getAsInt(),
                    linea.get("precioUnitario").getAsDouble(),
                    estadoPedido // <-- Usamos la variable de afuera, no linea.get("estado")
                ));
            }
        }
        return lista;
    }
}