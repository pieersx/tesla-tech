package teslatech;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id;
    private Cliente cliente;
    private Date fecha;
    private List<DetalleVenta> detalles;
    private double total;

    public Venta(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = new Date();
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        total += detalle.getSubtotal();
    }

    public double getTotal() { return total; }

    @Override
    public String toString() {
        return "Venta{id=" + id + ", cliente=" + cliente + ", fecha=" + fecha + ", total=" + total + ", detalles=" + detalles + '}';
    }
}
