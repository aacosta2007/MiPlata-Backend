package application.service;

import application.domain.enums.TipoMovimiento;
import application.util.FormValidationUtil;

public class TipoMovimientoSelector {

    public static TipoMovimiento seleccionarTipo() {
        boolean continuar = true;
        TipoMovimiento resultado = null;

        while (continuar) {
            System.out.print("""
                    1. Consignación
                    2. Retiro
                    3. Transferencia enviada
                    4. Transferencia recibida
                    5. Compra con tarjeta de crédito
                    6. Pago de tarjeta de crédito
                    """);

            int opcion = FormValidationUtil.validateInt("Seleccione el tipo de movimiento:");

            switch (opcion) {
                case 1 -> { resultado = TipoMovimiento.CONSIGNACION;      continuar = false; }
                case 2 -> { resultado = TipoMovimiento.RETIRO;            continuar = false; }
                case 3 -> { resultado = TipoMovimiento.TRANSFERENCIA_OUT; continuar = false; }
                case 4 -> { resultado = TipoMovimiento.TRANSFERENCIA_IN;  continuar = false; }
                case 5 -> { resultado = TipoMovimiento.COMPRA_TC;         continuar = false; }
                case 6 -> { resultado = TipoMovimiento.PAGO_TC;           continuar = false; }
                default -> System.out.println("Opción no válida, intente de nuevo.");
            }
        }

        return resultado;
    }
}
