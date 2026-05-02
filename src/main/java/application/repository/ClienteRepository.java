package application.repository;

import application.domain.Cliente;
import application.service.ports.ClienteRepositoryPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClienteRepository implements ClienteRepositoryPort {

    private final List<Cliente> clientes = new ArrayList<>(Arrays.asList(
            new Cliente(1, "1234567890", "Ana García",    "3001234567", "ana.garcia",   "pass123"),
            new Cliente(2, "0987654321", "Luis Martínez", "3109876543", "luis.martinez", "pass456"),
            new Cliente(3, "1122334455", "Sofia Ruiz",    "3201122334", "sofia.ruiz",    "pass789")
    ));

    @Override
    public Cliente saveCliente(Cliente cliente) {
        clientes.add(cliente);
        return cliente;
    }

    @Override
    public Cliente updateCliente(int id, Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) {
                clientes.set(i, cliente);
                return cliente;
            }
        }
        throw new IllegalArgumentException("Cliente con ID " + id + " no encontrado.");
    }

    @Override
    public Optional<Cliente> findClienteById(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<Cliente> findClienteByUsuario(String usuario) {
        return clientes.stream()
                .filter(c -> c.getUsuario().equalsIgnoreCase(usuario))
                .findFirst();
    }

    @Override
    public List<Cliente> findAllClientes() {
        return List.copyOf(clientes);
    }

    @Override
    public void deleteClienteById(int id) {
        boolean removed = clientes.removeIf(c -> c.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Cliente con ID " + id + " no encontrado.");
        }
    }
}
