package teslatech.modelos;

import java.sql.Date;

public class Cliente extends Persona {
    private int id;
    private Date fecha;

    public Cliente(String nombres, String dni, String direccion, String telefono, int id, Date fecha) {
        super(nombres, dni, direccion, telefono);
        this.id = id;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String obtenerDescription() {
        return "Este empleado tiene un Id=" + getId() + " y su fecha de ingreso es " + getFecha();
    }
}
