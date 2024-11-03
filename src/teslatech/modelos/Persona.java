package teslatech.modelos;

public class Persona {
    protected String nombres;
    protected String dni;
    protected String direccion;
    protected String telefono;

    public Persona() {
    }

    public Persona(String nombres, String dni, String direccion, String telefono) {
        this.nombres = nombres;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return "Persona = {" + "nombres=" + nombres + ", dni=" + dni +  ", direccion=" + direccion + ", telefono=" + telefono + "}";
    }
}
