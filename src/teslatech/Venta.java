package teslatech;

public class Venta {
    private int id;
    private int indiceDetalles;
    private Cliente cliente;
    private DetalleVenta[] detalles;

    private double total;

    public Venta(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.detalles = new DetalleVenta[100];
        this.indiceDetalles = 0;
        this.total = 0.0;
    }

    public double getTotal() {
        return total;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        if (indiceDetalles < detalles.length) {
            detalles[indiceDetalles] = detalle;
            indiceDetalles++;

            total += detalle.getSubTotal();
        } else {
            System.out.println("No se pueden agregar más detalles, el arreglo está lleno.");
        }
    }

    public void mostrarVenta() {
        System.out.println("ID = " + id);
        System.out.println("Cliente = " + cliente);
        System.out.println("Total = $" + total);
        System.out.println("Detalles = ");

        for (int i = 0; i < indiceDetalles; i++) {
            System.out.println("\t" + detalles[i]);
        }
    }
}
