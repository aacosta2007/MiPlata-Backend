package application.service;

import application.domain.Cliente;
import application.service.outputs.ClienteService;
import application.service.ports.ClienteRepositoryPort;

import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepositoryPort clienteRepositoryPort;

    public ClienteServiceImpl(ClienteRepositoryPort clienteRepositoryPort) {
        this.clienteRepositoryPort = clienteRepositoryPort;
    }

    @Override
    public Cliente createCliente(int id, String identificacion, String nombreCompleto,
                                 String celular, String usuario, String contrasena) {

        if (clienteRepositoryPort.findClienteById(id).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con el ID: " + id);
        }

        if (clienteRepositoryPort.findClienteByUsuario(usuario).isPresent()) {
            throw new IllegalArgumentException("El usuario '" + usuario + "' ya está en uso.");
        }

        Cliente cliente = new Cliente(id, identificacion, nombreCompleto, celular, usuario, contrasena);
        clienteRepositoryPort.saveCliente(cliente);
        return cliente;
    }

    @Override
    public Cliente updateCliente(int id, String identificacion, String nombreCompleto,
                                 String celular, String usuario) {

        Cliente clienteActual = clienteRepositoryPort.findClienteById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un cliente con el ID: " + id));

        Optional<Cliente> usuarioExistente = clienteRepositoryPort.findClienteByUsuario(usuario);
        if (usuarioExistente.isPresent() && usuarioExistente.get().getId() != id) {
            throw new IllegalArgumentException("El usuario '" + usuario + "' ya está en uso por otro cliente.");
        }

        clienteActual.editarPerfil(nombreCompleto, celular);
        clienteActual.setIdentificacion(identificacion);
        clienteActual.setUsuario(usuario);

        clienteRepositoryPort.updateCliente(id, clienteActual);
        return clienteActual;
    }

    @Override
    public Optional<Cliente> getClienteById(int id) {
        return clienteRepositoryPort.findClienteById(id);
    }

    @Override
    public Optional<Cliente> getClienteByUsuario(String usuario) {
        return clienteRepositoryPort.findClienteByUsuario(usuario);
    }
}
