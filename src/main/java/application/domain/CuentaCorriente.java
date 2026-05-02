package application.domain;

import application.domain.enums.EstadoCuenta;
import application.domain.enums.TipoMovimiento;

public class CuentaCorriente extends Cuenta {

    private double porcentajeSobregiro;
    private double limiteSobregiro;


    public CuentaCorriente() {
        super();
    }

    public CuentaCorriente(String numeroCuenta, double saldo, Cliente cliente,
                           double porcentajeSobregiro, double limiteSobregiro) {
        super(numeroCuenta, saldo, cliente);
        this.porcentajeSobregiro = porcentajeSobregiro;
        this.limiteSobregiro     = limiteSobregiro;
    }


    @Override
    public void retirar(double monto) {
        validarCuentaActiva("retirar");

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        }

        double saldoDisponible = calcularLimiteSobregiro();

        if (monto > saldoDisponible) {
            throw new IllegalStateException(
                    "Supera el límite disponible. Disponible con sobregiro: $"
                            + String.format("%,.2f", saldoDisponible));
        }

        this.saldo -= monto;

        if (this.saldo < 0) {
            double costoSobregiro = Math.abs(this.saldo) * porcentajeSobregiro;
            this.saldo -= costoSobregiro;
            construirMovimiento(TipoMovimiento.RETIRO, costoSobregiro,
                    "Costo de sobregiro (" + (porcentajeSobregiro * 100) + "%)");
        }

        construirMovimiento(TipoMovimiento.RETIRO, monto,
                "Retiro de $" + String.format("%,.2f", monto));
    }


    @Override
    public void transferir(Cuenta destino, double monto) {
        validarCuentaActiva("transferir");

        if (!validarDestino(destino)) {
            throw new IllegalArgumentException(
                    "La cuenta destino no es válida o no está activa.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }
        if (monto > calcularLimiteSobregiro()) {
            throw new IllegalStateException(
                    "Supera el límite disponible. Disponible: $"
                            + String.format("%,.2f", calcularLimiteSobregiro()));
        }

        this.saldo -= monto;
        construirMovimiento(TipoMovimiento.TRANSFERENCIA_OUT, monto,
                "Transferencia a cuenta " + destino.getNumeroCuenta());

        destino.construirMovimiento(TipoMovimiento.TRANSFERENCIA_IN, monto,
                "Transferencia desde cuenta " + this.numeroCuenta);
        destino.saldo += monto;
    }

    @Override
    public boolean validarDestino(Cuenta c) {
        return c != null && c.getEstado() == EstadoCuenta.ACTIVA;
    }


    public double calcularLimiteSobregiro() {
        return this.saldo + limiteSobregiro;
    }


    public double getPorcentajeSobregiro() { return porcentajeSobregiro; }
    public void setPorcentajeSobregiro(double porcentajeSobregiro) {
        this.porcentajeSobregiro = porcentajeSobregiro;
    }

    public double getLimiteSobregiro() { return limiteSobregiro; }
    public void setLimiteSobregiro(double limiteSobregiro) {
        this.limiteSobregiro = limiteSobregiro;
    }


    @Override
    public String toString() {
        return String.format(
                "CuentaCorriente { numero='%s' | saldo=$%,.2f | limSobregiro=$%,.2f | estado='%s' | cliente='%s' }",
                numeroCuenta, saldo, limiteSobregiro, estado.getDescripcion(), cliente.getNombreCompleto()
        );
    }
}
