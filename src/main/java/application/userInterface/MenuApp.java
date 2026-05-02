package application.userInterface;

import application.util.FormValidationUtil;
import application.view.ClienteView;
import application.view.MovimientoView;

public class MenuApp {

    private final ClienteView clienteView;
    private final MovimientoView movimientoView;

    public MenuApp(ClienteView clienteView, MovimientoView movimientoView) {
        this.clienteView = clienteView;
        this.movimientoView = movimientoView;
    }

    public void showMainMenu() {
        System.out.println("\n================================");
        System.out.println("       Bienvenido a MiPlata        ");
        System.out.println("================================");

        boolean activo = true;
        while (activo) {
            int opcion = FormValidationUtil.validateInt("""
                    \nSeleccione una opción:
                    1. Gestión de Clientes
                    2. Gestión de Movimientos
                    3. Salir
                    """);

            switch (opcion) {
                case 1 -> showClienteMenu();
                case 2 -> showMovimientoMenu();
                case 3 -> {
                    System.out.println("Hasta luego.");
                    activo = false;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void showClienteMenu() {
        boolean activo = true;
        while (activo) {
            int opcion = FormValidationUtil.validateInt("""
                    \n--- Menú Clientes ---
                    1. Registrar cliente
                    2. Buscar cliente por ID
                    3. Listar todos los clientes
                    4. Actualizar cliente
                    5. Eliminar cliente
                    6. Desbloquear cliente
                    7. Volver
                    """);

            switch (opcion) {
                case 1 -> clienteView.createCliente();
                case 2 -> clienteView.getClienteById();
                case 3 -> clienteView.getAllClientes();
                case 4 -> clienteView.updateCliente();
                case 5 -> clienteView.deleteCliente();
                case 6 -> clienteView.desbloquearCliente();
                case 7 -> activo = false;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void showMovimientoMenu() {
        boolean activo = true;
        while (activo) {
            int opcion = FormValidationUtil.validateInt("""
                    \n--- Menú Movimientos ---
                    1. Registrar movimiento
                    2. Buscar movimiento por ID
                    3. Ver historial completo
                    4. Eliminar movimiento
                    5. Volver
                    """);

            switch (opcion) {
                case 1 -> movimientoView.createMovimiento();
                case 2 -> movimientoView.getMovimientoById();
                case 3 -> movimientoView.getAllMovimientos();
                case 4 -> movimientoView.deleteMovimiento();
                case 5 -> activo = false;
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}
