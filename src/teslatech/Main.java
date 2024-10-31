package teslatech;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializar servicios
        ServicioProducto servicioProducto = new ServicioProducto();
        ServicioCliente servicioCliente = new ServicioCliente();
        ServicioVenta servicioVenta = new ServicioVenta();

        // Crear productos y clientes
        Producto p1 = new Producto(1, "Laptop", 1200.0, 10);
        Producto p2 = new Producto(2, "Mouse", 25.0, 50);
        Cliente c1 = new Cliente(1, "Juan Pérez", "juan@example.com");

        servicioProducto.agregarProducto(p1);
        servicioProducto.agregarProducto(p2);
        servicioCliente.agregarCliente(c1);

        // Crear detalle de venta y realizar venta
        List<DetalleVenta> detalles = new ArrayList<>();
        detalles.add(new DetalleVenta(p1, 1));
        detalles.add(new DetalleVenta(p2, 2));

        System.out.println("=== Realizando Venta ===");
        servicioVenta.realizarVenta(c1, detalles);

        // Mostrar productos actualizados
        System.out.println("\n=== Productos Después de la Venta ===");
        servicioProducto.mostrarProductos();
    }
}
