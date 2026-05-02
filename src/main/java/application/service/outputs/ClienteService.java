package application.service.outputs;

import application.domain.Cliente;

import java.util.Optional;

public interface ClienteService {

    Cliente createCliente(int id, String identificacion, String nombreCompleto,
                          String celular, String usuario, String contrasena);

    Cliente updateCliente(int id, String identificacion, String nombreCompleto,
                          String celular, String usuario);

    Optional<Cliente> getClienteById(int id);

    Optional<Cliente> getClienteByUsuario(String usuario);
}
