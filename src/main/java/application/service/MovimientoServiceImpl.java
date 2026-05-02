package application.service;

import application.domain.Movimiento;
import application.domain.enums.TipoMovimiento;
import application.service.outputs.MovimientoService;
import application.service.ports.MovimientoRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepositoryPort movimientoRepositoryPort;

    public MovimientoServiceImpl(MovimientoRepositoryPort movimientoRepositoryPort) {
        this.movimientoRepositoryPort = movimientoRepositoryPort;
    }

    @Override
    public Movimiento createMovimiento(int id, TipoMovimiento tipo, double valor,
                                       double saldoPosterior, String descripcion) {

        if (movimientoRepositoryPort.findMovimientoById(id).isPresent()) {
            throw new IllegalArgumentException("Ya existe un movimiento con el ID: " + id);
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("El valor del movimiento debe ser mayor a cero.");
        }

        Movimiento movimiento = new Movimiento(id, LocalDateTime.now(), tipo,
                valor, saldoPosterior, descripcion);

        movimientoRepositoryPort.saveMovimiento(movimiento);
        return movimiento;
    }

    @Override
    public Optional<Movimiento> getMovimientoById(int id) {
        return movimientoRepositoryPort.findMovimientoById(id);
    }

    @Override
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepositoryPort.findAllMovimientos();
    }

    @Override
    public void deleteMovimientoById(int id) {
        movimientoRepositoryPort.findMovimientoById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un movimiento con el ID: " + id));

        movimientoRepositoryPort.deleteMovimientoById(id);
    }
}
