package application.domain;

import application.domain.enums.EstadoCuenta;
import application.domain.enums.TipoMovimiento;

public class CuentaAhorros extends Cuenta {

    private double tasaInteres;


    public CuentaAhorros() {
        super();
    }

    public CuentaAhorros(String numeroCuenta, double saldo, Cliente cliente, double tasaInteres) {
        super(numeroCuenta, saldo, cliente);
        this.tasaInteres = tasaInteres;
    }


    @Override
    public void retirar(double monto) {
        validarCuentaActiva("retirar");

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        }
        if (monto > this.saldo) {
            throw new IllegalStateException(
                    "Saldo insuficiente. Disponible: $" + String.format("%,.2f", saldo));
        }

        this.saldo -= monto;
        construirMovimiento(TipoMovimiento.RETIRO, monto,
                "Retiro de $" + String.format("%,.2f", monto));
    }


    @Override
    public void transferir(Cuenta destino, double monto) {
        validarCuentaActiva("transferir");

        if (!validarDestino(destino)) {
            throw new IllegalArgumentException(
                    "CuentaAhorros solo permite transferencias a cuentas del mismo cliente.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }
        if (monto > this.saldo) {
            throw new IllegalStateException(
                    "Saldo insuficiente. Disponible: $" + String.format("%,.2f", saldo));
        }

        this.saldo -= monto;
        construirMovimiento(TipoMovimiento.TRANSFERENCIA_OUT, monto,
                "Transferencia a cuenta " + destino.getNumeroCuenta());

        destino.consignar(monto);
        destino.construirMovimiento(TipoMovimiento.TRANSFERENCIA_IN, monto,
                "Transferencia desde cuenta " + this.numeroCuenta);
    }

    @Override
    public boolean validarDestino(Cuenta c) {
        return c != null
                && c.getEstado() == EstadoCuenta.ACTIVA
                && c.getCliente().getId() == this.cliente.getId();
    }


    public double calcularIntereses() {
        return this.saldo * tasaInteres;
    }

    public void aplicarIntereses() {
        double intereses = calcularIntereses();
        this.saldo += intereses;
        construirMovimiento(TipoMovimiento.CONSIGNACION, intereses,
                "Intereses aplicados (" + (tasaInteres * 100) + "%)");
    }


    public double getTasaInteres() { return tasaInteres; }
    public void setTasaInteres(double tasaInteres) { this.tasaInteres = tasaInteres; }


    @Override
    public String toString() {
        return String.format(
                "CuentaAhorros { numero='%s' | saldo=$%,.2f | tasa=%.2f%% | estado='%s' | cliente='%s' }",
                numeroCuenta, saldo, tasaInteres * 100, estado.getDescripcion(), cliente.getNombreCompleto()
        );
    }
}
