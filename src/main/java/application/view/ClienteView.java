package application.view;

import application.domain.Cliente;
import application.service.outputs.ClienteAdminService;
import application.service.outputs.ClienteService;
import application.util.FormValidationUtil;

import java.util.List;

public class ClienteView {

    private final ClienteService clienteService;
    private final ClienteAdminService clienteAdminService;

    public ClienteView(ClienteService clienteService, ClienteAdminService clienteAdminService) {
        this.clienteService = clienteService;
        this.clienteAdminService = clienteAdminService;
    }

    public void createCliente() {
        System.out.println("\n--- Registrar nuevo cliente ---");
        try {
            int id                   = FormValidationUtil.validateInt("ID del cliente:");
            String identificacion    = FormValidationUtil.validateString("Número de identificación:");
            String nombreCompleto    = FormValidationUtil.validateString("Nombre completo:");
            String celular           = FormValidationUtil.validateString("Celular:");
            String usuario           = FormValidationUtil.validateString("Nombre de usuario:");
            String contrasena        = FormValidationUtil.validateString("Contraseña:");

            Cliente creado = clienteService.createCliente(
                    id, identificacion, nombreCompleto, celular, usuario, contrasena);

            System.out.println("✓ Cliente registrado exitosamente. ID: " + creado.getId());

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getClienteById() {
        System.out.println("\n--- Buscar cliente por ID ---");
        try {
            int id = FormValidationUtil.validateInt("ID del cliente:");

            Cliente cliente = clienteService.getClienteById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No se encontró un cliente con ID: " + id));

            printCliente(cliente);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAllClientes() {
        System.out.println("\n--- Lista de clientes ---");

        List<Cliente> clientes = clienteAdminService.getAllClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.printf("%-4s %-14s %-22s %-12s %-18s %-10s%n",
                "ID", "Identificación", "Nombre", "Celular", "Usuario", "Estado");
        System.out.println("-".repeat(82));

        for (Cliente c : clientes) {
            System.out.printf("%-4d %-14s %-22s %-12s %-18s %-10s%n",
                    c.getId(),
                    c.getIdentificacion(),
                    c.getNombreCompleto(),
                    c.getCelular(),
                    c.getUsuario(),
                    c.isBloqueado() ? "BLOQUEADO" : "Activo");
        }
    }

    public void updateCliente() {
        System.out.println("\n--- Actualizar cliente ---");
        int id = FormValidationUtil.validateInt("ID del cliente a actualizar:");

        try {
            Cliente actual = clienteService.getClienteById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe un cliente con ID: " + id));

            String identificacion = actual.getIdentificacion();
            String nombreCompleto = actual.getNombreCompleto();
            String celular        = actual.getCelular();
            String usuario        = actual.getUsuario();

            printCliente(actual);

            int opcion = FormValidationUtil.validateInt("""
                    \nSeleccione el campo a actualizar:
                    1. Número de identificación
                    2. Nombre completo
                    3. Celular
                    4. Usuario
                    """);

            switch (opcion) {
                case 1 -> identificacion = FormValidationUtil.validateString(
                        "Nueva identificación (actual: " + identificacion + "):");
                case 2 -> nombreCompleto = FormValidationUtil.validateString(
                        "Nuevo nombre completo (actual: " + nombreCompleto + "):");
                case 3 -> celular = FormValidationUtil.validateString(
                        "Nuevo celular (actual: " + celular + "):");
                case 4 -> usuario = FormValidationUtil.validateString(
                        "Nuevo usuario (actual: " + usuario + "):");
                default -> {
                    System.out.println("Opción no válida.");
                    return;
                }
            }

            clienteService.updateCliente(id, identificacion, nombreCompleto, celular, usuario);
            System.out.println("✓ Cliente actualizado exitosamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteCliente() {
        System.out.println("\n--- Eliminar cliente ---");
        try {
            int id = FormValidationUtil.validateInt("ID del cliente a eliminar:");
            clienteAdminService.deleteClienteById(id);
            System.out.println("✓ Cliente con ID " + id + " eliminado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void desbloquearCliente() {
        System.out.println("\n--- Desbloquear cliente ---");
        try {
            int id = FormValidationUtil.validateInt("ID del cliente a desbloquear:");
            clienteAdminService.desbloquearCliente(id);
            System.out.println("✓ Cliente con ID " + id + " desbloqueado exitosamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printCliente(Cliente c) {
        System.out.println("""

                ==============================
                  Datos del cliente
                ==============================""");
        System.out.println("  ID             : " + c.getId());
        System.out.println("  Identificación : " + c.getIdentificacion());
        System.out.println("  Nombre         : " + c.getNombreCompleto());
        System.out.println("  Celular        : " + c.getCelular());
        System.out.println("  Usuario        : " + c.getUsuario());
        System.out.println("  Bloqueado      : " + (c.isBloqueado() ? "SÍ ⚠" : "No"));
        System.out.println("  Int. fallidos  : " + c.getIntentosFallidos());
    }
}
