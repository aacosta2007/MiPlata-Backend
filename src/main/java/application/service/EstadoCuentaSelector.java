package application.service;

import application.domain.enums.EstadoCuenta;
import application.util.FormValidationUtil;

public class EstadoCuentaSelector {

    public static EstadoCuenta seleccionarEstado() {
        boolean continuar = true;
        EstadoCuenta resultado = null;

        while (continuar) {
            System.out.print("""
                    1. Activa
                    2. Inactiva
                    3. Bloqueada
                    4. Cerrada
                    """);

            int opcion = FormValidationUtil.validateInt("Seleccione el estado de la cuenta:");

            switch (opcion) {
                case 1 -> { resultado = EstadoCuenta.ACTIVA;    continuar = false; }
                case 2 -> { resultado = EstadoCuenta.INACTIVA;  continuar = false; }
                case 3 -> { resultado = EstadoCuenta.BLOQUEADA; continuar = false; }
                case 4 -> { resultado = EstadoCuenta.CERRADA;   continuar = false; }
                default -> System.out.println("Opción no válida, intente de nuevo.");
            }
        }

        return resultado;
    }
}
