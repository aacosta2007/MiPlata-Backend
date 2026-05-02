package application.domain.interfaces;

import application.domain.Movimiento;
import java.util.List;

public interface ITransaction {

    void consignar(double monto);

    void retirar(double monto);

    double consultarSaldo();

    List<Movimiento> obtenerMovimientos();
}
