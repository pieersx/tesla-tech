package teslatech.servicios;

import teslatech.modelos.Cliente;

public class ServicioCliente {
    private Cliente[] clientes;
    private int index;

    public ServicioCliente() {
        clientes = new Cliente[100];
        this.index = 0;
    }

    public void agregarCliente(Cliente cliente) {
        if (index < clientes.length) {
            clientes[index] = cliente;
            index++;
        } else {
            System.out.println("No se pueden agregar más clientes. Límite alcanzado.");
        }
    }

    public Cliente buscarCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }
}
