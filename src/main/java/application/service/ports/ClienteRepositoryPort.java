package application.service.ports;

import application.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {

    Cliente saveCliente(Cliente cliente);

    Cliente updateCliente(int id, Cliente cliente);

    Optional<Cliente> findClienteById(int id);

    Optional<Cliente> findClienteByUsuario(String usuario);

    List<Cliente> findAllClientes();

    void deleteClienteById(int id);
}
