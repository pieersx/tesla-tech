package teslatech.servicios;

import teslatech.modelos.Cliente;
import teslatech.modelos.DetalleVenta;
import teslatech.modelos.Venta;

public class ServicioVenta {
    private Venta[] ventas;
    private int contadorVentas = 1;
    private int index = 0;

    public ServicioVenta() {
        ventas = new Venta[100];
    }

    public void realizarVenta(Cliente cliente, DetalleVenta[] detalles) {
        Venta venta = new Venta(contadorVentas++, cliente);
        for (DetalleVenta detalle : detalles) {
            if (detalle != null) {
                venta.agregarDetalle(detalle);
                detalle.getProducto().reducirStock(detalle.getCantidad());
            }
        }
        ventas[index] = venta;
        index++;
        System.out.println("Venta realizada: " + venta);
    }
}
