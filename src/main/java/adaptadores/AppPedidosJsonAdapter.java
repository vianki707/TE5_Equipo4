/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        
        // Parseamos el texto JSON a un arreglo de Gson
        JsonArray pedidos = JsonParser.parseString(txt).getAsJsonArray();
        
        // Recorremos cada pedido
        for (JsonElement elemento : pedidos) {
            JsonObject pedido = elemento.getAsJsonObject();
            JsonArray lineas = pedido.getAsJsonArray("lineas");
            
            // Recorremos las líneas dentro del pedido
            for (JsonElement lineaElemento : lineas) {
                JsonObject linea = lineaElemento.getAsJsonObject();
                
                // Extraemos los datos y creamos el record LineaPedido
                lista.add(new LineaPedido(
                    linea.get("platoId").getAsInt(),
                    linea.get("cantidad").getAsInt(),
                    linea.get("precioUnitario").getAsDouble(),
                    linea.get("estadoPedido").getAsString()
                ));
            }
        }
        return lista;
    }
}