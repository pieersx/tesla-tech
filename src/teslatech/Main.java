package teslatech;

import teslatech.servicios.*;
import teslatech.modelos.*;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        // Inicializar servicios
        ServicioProducto servicioProducto = new ServicioProducto();
        ServicioCliente servicioCliente = new ServicioCliente();
        ServicioVenta servicioVenta = new ServicioVenta();

        // Crear productos y clientes
        Producto p1 = new Producto(1, "Laptop", 1200.0, 10);
        Producto p2 = new Producto(2, "Mouse", 25.0, 50);
        Cliente c1 = new Cliente("Juan Pérez", "12345678", "Av. Las Palmas", "987654321",  1, new Date(System.currentTimeMillis()));

        servicioProducto.agregarProducto(p1);
        servicioProducto.agregarProducto(p2);
        servicioCliente.agregarCliente(c1);

        // Crear detalle de venta y realizar venta
        DetalleVenta[] detalles = new DetalleVenta[4];
        detalles[0] = new DetalleVenta(p1, 1);
        detalles[1] = new DetalleVenta(p2, 2);

        servicioVenta.realizarVenta(c1, detalles);

        // Mostrar productos actualizados
        System.out.println("\nProductos después de la venta:");
        servicioProducto.mostrarProductos();
    }
}
