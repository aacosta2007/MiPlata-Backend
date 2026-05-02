package application.view;

import application.domain.Movimiento;
import application.domain.enums.TipoMovimiento;
import application.service.TipoMovimientoSelector;
import application.service.outputs.MovimientoService;
import application.util.FormValidationUtil;

import java.util.List;

public class MovimientoView {

    private final MovimientoService movimientoService;

    public MovimientoView(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    public void createMovimiento() {
        System.out.println("\n--- Registrar movimiento ---");
        try {
            int id = FormValidationUtil.validateInt("ID del movimiento:");

            System.out.println("Tipo de movimiento:");
            TipoMovimiento tipo = TipoMovimientoSelector.seleccionarTipo();

            double valor          = FormValidationUtil.validateDouble("Valor ($):");
            double saldoPosterior = FormValidationUtil.validateDouble("Saldo posterior ($):");
            String descripcion    = FormValidationUtil.validateString("Descripción:");

            Movimiento creado = movimientoService.createMovimiento(
                    id, tipo, valor, saldoPosterior, descripcion);

            System.out.println("\n✓ Movimiento registrado:");
            printMovimiento(creado);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getMovimientoById() {
        System.out.println("\n--- Buscar movimiento por ID ---");
        try {
            int id = FormValidationUtil.validateInt("ID del movimiento:");

            Movimiento movimiento = movimientoService.getMovimientoById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No se encontró un movimiento con ID: " + id));

            printMovimiento(movimiento);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAllMovimientos() {
        System.out.println("\n--- Historial de movimientos ---");

        List<Movimiento> movimientos = movimientoService.getAllMovimientos();

        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }

        System.out.println("-".repeat(90));
        for (Movimiento m : movimientos) {
            System.out.println(m);
        }
        System.out.println("-".repeat(90));
        System.out.println("Total de movimientos: " + movimientos.size());
    }

    public void deleteMovimiento() {
        System.out.println("\n--- Eliminar movimiento ---");
        System.out.println("NOTA: Solo use esta opción en casos excepcionales.");
        System.out.println("Los movimientos reales se anulan con contra-asientos.\n");

        try {
            int id = FormValidationUtil.validateInt("ID del movimiento a eliminar:");

            Movimiento movimiento = movimientoService.getMovimientoById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe un movimiento con ID: " + id));

            printMovimiento(movimiento);

            String confirmacion = FormValidationUtil.validateString(
                    "\n¿Confirma la eliminación? (si / no):");

            if (confirmacion.equalsIgnoreCase("si")) {
                movimientoService.deleteMovimientoById(id);
                System.out.println("✓ Movimiento con ID " + id + " eliminado.");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printMovimiento(Movimiento m) {
        System.out.println("""

                =======================================
                  Detalle del movimiento
                =======================================""");
        System.out.println("  ID             : " + m.getId());
        System.out.println("  Fecha y hora   : " + m.getFechaHoraFormateada());
        System.out.println("  Tipo           : " + m.getTipo().getDescripcion());
        System.out.println("  Valor          : $" + String.format("%,.2f", m.getValor()));
        System.out.println("  Saldo posterior: $" + String.format("%,.2f", m.getSaldoPosterior()));
        System.out.println("  Descripción    : " + m.getDescripcion());
    }
}
