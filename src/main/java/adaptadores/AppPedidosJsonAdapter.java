package adaptadores;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.CambioEstado;
import modelo.LineaPedido;

/** Lee pedidos_app.json: un objeto por pedido, que se convierte en una LineaPedido por plato. */
public class AppPedidosJsonAdapter extends FuenteDatos<LineaPedido> {

    public AppPedidosJsonAdapter(Path ruta) {
        super(ruta);
    }

    @Override
    protected List<LineaPedido> convertir(String contenido) {
        List<LineaPedido> resultado = new ArrayList<>();
        JsonArray pedidos = JsonParser.parseString(contenido).getAsJsonArray();

        for (JsonElement elemento : pedidos) {
            JsonObject pedido = elemento.getAsJsonObject();
            String numeroPedido = pedido.get("numeroPedido").getAsString();
            double total = pedido.get("total").getAsDouble();
            LocalDateTime registradoEn = LocalDateTime.parse(pedido.get("registradoEn").getAsString());

            // El historial es del pedido: se lee una vez y se comparte entre sus líneas.
            List<CambioEstado> historial = new ArrayList<>();
            for (JsonElement e : pedido.getAsJsonArray("historialEstados")) {
                JsonObject cambio = e.getAsJsonObject();
                historial.add(new CambioEstado(
                        cambio.get("estado").getAsString(),
                        LocalDateTime.parse(cambio.get("fechaHora").getAsString())));
            }
            List<CambioEstado> historialInmutable = List.copyOf(historial);

            int numeroLinea = 1;
            for (JsonElement l : pedido.getAsJsonArray("lineas")) {
                JsonObject linea = l.getAsJsonObject();
                resultado.add(new LineaPedido(
                        numeroPedido,
                        numeroLinea++,
                        linea.get("platoId").getAsInt(),
                        linea.get("cantidad").getAsInt(),
                        linea.get("precioUnitario").getAsDouble(),
                        total,
                        registradoEn,
                        historialInmutable));
            }
        }
        return resultado;
    }
}
