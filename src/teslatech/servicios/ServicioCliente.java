package teslatech.servicios;

import teslatech.interfaces.Operaciones;
import teslatech.modelos.Cliente;

public class ServicioCliente implements Operaciones<Cliente> {
    private Cliente[] clientes;
    private int index;

    public ServicioCliente() {
        clientes = new Cliente[MAX];
        this.index = 0;
    }

    public void agregar(Cliente cliente) {
        if (index < clientes.length) {
            clientes[index] = cliente;
            index++;
        } else {
            System.out.println("No se pueden agregar más clientes. Límite alcanzado.");
        }
    }

    public Cliente buscarPorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }

}
