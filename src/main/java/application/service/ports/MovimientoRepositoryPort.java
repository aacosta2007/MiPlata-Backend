package application.service.ports;

import application.domain.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoRepositoryPort {

    Movimiento saveMovimiento(Movimiento movimiento);

    Optional<Movimiento> findMovimientoById(int id);

    List<Movimiento> findAllMovimientos();

    void deleteMovimientoById(int id);
}
