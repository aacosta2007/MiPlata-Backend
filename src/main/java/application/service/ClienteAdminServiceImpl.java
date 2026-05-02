package application.service;

import application.domain.Cliente;
import application.service.outputs.ClienteAdminService;
import application.service.ports.ClienteRepositoryPort;

import java.util.List;

public class ClienteAdminServiceImpl implements ClienteAdminService {

    private final ClienteRepositoryPort clienteRepositoryPort;

    public ClienteAdminServiceImpl(ClienteRepositoryPort clienteRepositoryPort) {
        this.clienteRepositoryPort = clienteRepositoryPort;
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepositoryPort.findAllClientes();
    }

    @Override
    public void deleteClienteById(int id) {
        clienteRepositoryPort.findClienteById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se puede eliminar: cliente con ID " + id + " no encontrado."));

        clienteRepositoryPort.deleteClienteById(id);
    }

    @Override
    public void desbloquearCliente(int id) {
        Cliente cliente = clienteRepositoryPort.findClienteById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un cliente con el ID: " + id));

        if (!cliente.isBloqueado()) {
            throw new IllegalStateException("El cliente con ID " + id + " no está bloqueado.");
        }

        cliente.desbloquear();
        clienteRepositoryPort.updateCliente(id, cliente);
    }
}
