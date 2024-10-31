package teslatech;

import java.util.ArrayList;
import java.util.List;

public class ServicioVenta {
    private List<Venta> ventas = new ArrayList<>();
    private int contadorVentas = 1;

    public void realizarVenta(Cliente cliente, List<DetalleVenta> detalles) {
        Venta venta = new Venta(contadorVentas++, cliente);
        for (DetalleVenta detalle : detalles) {
            venta.agregarDetalle(detalle);
            detalle.getProducto().reducirStock(detalle.getCantidad());
        }
        ventas.add(venta);
        System.out.println("Venta realizada: " + venta);
    }
}
