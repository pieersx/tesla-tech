package teslatech;

public class ServicioProducto {
    private Producto[] productos = new Producto[100];
    private int indiceProductos = 0;

    public void agregarProducto(Producto producto) {
        if (indiceProductos <= productos.length) {
            productos[indiceProductos] = producto;
            indiceProductos++;
        } else {

        }
    }

    public Producto buscaProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void mostrarProductos() {
        for (Producto p : productos) {
            System.out.println(p);
        }
    }
}
