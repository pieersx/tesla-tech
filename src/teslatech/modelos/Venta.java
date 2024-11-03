package teslatech.modelos;

import java.util.Date;

public class Venta {
    private int id;
    private Cliente cliente;
    private Date fecha;
    private DetalleVenta[] detalles;
    private double total;
    private int index;

    public Venta(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = new Date();
        this.detalles = new DetalleVenta[100];
        this.total = 0.0;
        this.index = 0;
    }

    public double getTotal() {
        return total;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        if (index < detalles.length) {
            detalles[index] = detalle;
            index++;

            total += detalle.getSubtotal();
        } else {
            System.out.println("No se pueden agregar más detalles. Límite alcanzado.");
        }
    }


    public String toString() {
        StringBuilder detallesStr = new StringBuilder();
        for (int i = 0; i < index; i++) {
            detallesStr.append(detalles[i].toString());
            if (i < index - 1) {
                detallesStr.append(", ");
            }
        }
        return "Venta{id=" + id + ", cliente=" + cliente + ", fecha=" + fecha + ", total=" + total + ", detalles=[" + detallesStr + "]}";
    }
}
