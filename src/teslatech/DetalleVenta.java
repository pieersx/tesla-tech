package teslatech;

public class DetalleVenta {
    private Producto producto;
    private int cantidad;
    private double subTotal;

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subTotal = producto.getPrecio() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String toString() {
        return "Detalle de Venta { Producto = '" + producto.getNombre() + "', Cantidad = " + cantidad + ", Subtotal = $" + subTotal + " }";
    }
}
