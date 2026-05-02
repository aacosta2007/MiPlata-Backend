package application.service.outputs;

import application.domain.Movimiento;
import application.domain.enums.TipoMovimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoService {

    Movimiento createMovimiento(int id, TipoMovimiento tipo, double valor,
                                double saldoPosterior, String descripcion);

    Optional<Movimiento> getMovimientoById(int id);

    List<Movimiento> getAllMovimientos();

    void deleteMovimientoById(int id);
}
