package teslatech.servicios;

import teslatech.interfaces.Operaciones;
import teslatech.modelos.Producto;

public class ServicioProducto implements Operaciones<Producto> {
    private Producto[] productos;
    private int index;

    public ServicioProducto() {
        productos = new Producto[MAX];
        this.index = 0;
    }

    public void agregar(Producto producto) {
        if (index < productos.length) {
            productos[index] = producto;
            index++;
        } else {
            System.out.println("No se pueden agregar más productos. Límite alcanzado.");
        }
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public void mostrarProductos() {
        for (int i = 0; i < index; i++) {
            System.out.println(productos[i]);
        }
    }
}
