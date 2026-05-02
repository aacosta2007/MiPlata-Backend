package application.domain;

import application.domain.enums.EstadoCuenta;
import application.domain.enums.TipoMovimiento;
import application.domain.interfaces.ITransaction;
import application.domain.interfaces.ITransferible;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta implements ITransaction, ITransferible {

    protected String numeroCuenta;
    protected double saldo;
    protected LocalDateTime fechaApertura;
    protected EstadoCuenta estado;
    protected List<Movimiento> movimientos;
    protected Cliente cliente;


    public Cuenta() {
        this.movimientos = new ArrayList<>();
    }

    public Cuenta(String numeroCuenta, double saldo, Cliente cliente) {
        this.numeroCuenta = numeroCuenta;
        this.saldo        = saldo;
        this.cliente      = cliente;
        this.fechaApertura = LocalDateTime.now();
        this.estado        = EstadoCuenta.ACTIVA;
        this.movimientos   = new ArrayList<>();
    }


    @Override
    public double consultarSaldo() {
        return saldo;
    }

    @Override
    public void consignar(double monto) {
        validarCuentaActiva("consignar");
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a consignar debe ser mayor a cero.");
        }
        this.saldo += monto;
        construirMovimiento(TipoMovimiento.CONSIGNACION, monto,
                "Consignación de $" + String.format("%,.2f", monto));
    }

    @Override
    public List<Movimiento> obtenerMovimientos() {
        return List.copyOf(movimientos);
    }

    @Override
    public abstract void retirar(double monto);


    @Override
    public abstract void transferir(Cuenta destino, double monto);

    @Override
    public abstract boolean validarDestino(Cuenta c);


    protected Movimiento construirMovimiento(TipoMovimiento tipo, double valor, String descripcion) {
        int nuevoId = movimientos.size() + 1;
        Movimiento m = new Movimiento(nuevoId, LocalDateTime.now(), tipo, valor, this.saldo, descripcion);
        movimientos.add(m);
        return m;
    }

    protected void validarCuentaActiva(String operacion) {
        if (estado != EstadoCuenta.ACTIVA) {
            throw new IllegalStateException(
                    "No se puede " + operacion + " en una cuenta " + estado.getDescripcion() + ".");
        }
    }


    public String getNumeroCuenta()  { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public double getSaldo() { return saldo; }

    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }

    public LocalDateTime getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDateTime fechaApertura) { this.fechaApertura = fechaApertura; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }


    @Override
    public String toString() {
        return String.format(
                "Cuenta { numero='%s' | saldo=$%,.2f | estado='%s' | cliente='%s' }",
                numeroCuenta, saldo, estado.getDescripcion(), cliente.getNombreCompleto()
        );
    }
}
