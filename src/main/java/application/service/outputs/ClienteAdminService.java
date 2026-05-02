package application.service.outputs;

import application.domain.Cliente;

import java.util.List;

public interface ClienteAdminService {

    List<Cliente> getAllClientes();

    void deleteClienteById(int id);

    void desbloquearCliente(int id);
}
