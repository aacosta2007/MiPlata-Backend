package application.repository;

import application.domain.Movimiento;
import application.service.ports.MovimientoRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovimientoRepository implements MovimientoRepositoryPort {

    private final List<Movimiento> movimientos = new ArrayList<>();

    @Override
    public Movimiento saveMovimiento(Movimiento movimiento) {
        movimientos.add(movimiento);
        return movimiento;
    }

    @Override
    public Optional<Movimiento> findMovimientoById(int id) {
        return movimientos.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    @Override
    public List<Movimiento> findAllMovimientos() {
        return List.copyOf(movimientos);
    }

    @Override
    public void deleteMovimientoById(int id) {
        boolean removed = movimientos.removeIf(m -> m.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Movimiento con ID " + id + " no encontrado.");
        }
    }
}
