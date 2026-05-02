package application.domain;

import application.domain.enums.EstadoCuenta;
import application.domain.enums.TipoMovimiento;

public class TarjetaCredito extends Cuenta {

    private double cupo;
    private double deuda;
    private int numeroCuotas;


    public TarjetaCredito() {
        super();
    }

    public TarjetaCredito(String numeroCuenta, Cliente cliente, double cupo) {
        super(numeroCuenta, cupo, cliente);
        this.cupo         = cupo;
        this.deuda        = 0;
        this.numeroCuotas = 1;
    }


    @Override
    public void retirar(double monto) {
        validarCuentaActiva("realizar avance");

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del avance debe ser mayor a cero.");
        }

        double cupoDisponible = cupo - deuda;

        if (monto > cupoDisponible) {
            throw new IllegalStateException(
                    "Cupo insuficiente. Disponible: $" + String.format("%,.2f", cupoDisponible));
        }

        double costoAvance = monto * 0.05;
        this.deuda  += monto + costoAvance;
        this.saldo   = cupo - deuda;

        construirMovimiento(TipoMovimiento.RETIRO, monto,
                "Avance en efectivo. Costo del avance: $" + String.format("%,.2f", costoAvance));
    }


    @Override
    public void transferir(Cuenta destino, double monto) {
        validarCuentaActiva("transferir");

        if (!validarDestino(destino)) {
            throw new IllegalArgumentException(
                    "La TC solo puede transferir para pagar deuda de otra TC del mismo cliente.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero.");
        }

        double cupoDisponible = cupo - deuda;
        if (monto > cupoDisponible) {
            throw new IllegalStateException(
                    "Cupo insuficiente. Disponible: $" + String.format("%,.2f", cupoDisponible));
        }

        this.deuda += monto;
        this.saldo  = cupo - deuda;

        TarjetaCredito tc = (TarjetaCredito) destino;
        tc.pagar(monto);

        construirMovimiento(TipoMovimiento.TRANSFERENCIA_OUT, monto,
                "Pago de deuda TC " + destino.getNumeroCuenta());
    }

    @Override
    public boolean validarDestino(Cuenta c) {
        return c instanceof TarjetaCredito
                && c.getEstado() == EstadoCuenta.ACTIVA
                && c.getCliente().getId() == this.cliente.getId();
    }


    public void comprar(double monto, int cuotas) {
        validarCuentaActiva("comprar");

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto de la compra debe ser mayor a cero.");
        }
        if (cuotas < 1) {
            throw new IllegalArgumentException("El número de cuotas debe ser al menos 1.");
        }

        double cupoDisponible = cupo - deuda;
        if (monto > cupoDisponible) {
            throw new IllegalStateException(
                    "Cupo insuficiente. Disponible: $" + String.format("%,.2f", cupoDisponible));
        }

        this.numeroCuotas = cuotas;
        this.deuda       += monto;
        this.saldo        = cupo - deuda;

        construirMovimiento(TipoMovimiento.COMPRA_TC, monto,
                "Compra en " + cuotas + " cuota(s). Cuota mensual: $"
                        + String.format("%,.2f", calcularCuotaMensual()));
    }

    public void pagar(double monto) {
        validarCuentaActiva("pagar");

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }
        if (monto > deuda) {
            throw new IllegalArgumentException(
                    "El pago supera la deuda actual ($" + String.format("%,.2f", deuda) + ").");
        }

        this.deuda -= monto;
        this.saldo  = cupo - deuda;

        construirMovimiento(TipoMovimiento.PAGO_TC, monto,
                "Pago de TC. Deuda restante: $" + String.format("%,.2f", deuda));
    }

    public double calcularTasa(int cuotas) {
        if (cuotas <= 1)  return 0.00;
        if (cuotas <= 6)  return 0.015;
        if (cuotas <= 12) return 0.020;
        return 0.025;
    }

    public double calcularCuotaMensual() {
        if (numeroCuotas <= 1) return deuda;
        double tasa = calcularTasa(numeroCuotas);
        return deuda * (tasa / (1 - Math.pow(1 + tasa, -numeroCuotas)));
    }

    public double getCupoDisponible() {
        return cupo - deuda;
    }


    public double getCupo()  { return cupo; }
    public void setCupo(double cupo) { this.cupo = cupo; }

    public double getDeuda() { return deuda; }
    public void setDeuda(double deuda) { this.deuda = deuda; }

    public int getNumeroCuotas() { return numeroCuotas; }
    public void setNumeroCuotas(int numeroCuotas) { this.numeroCuotas = numeroCuotas; }


    @Override
    public String toString() {
        return String.format(
                "TarjetaCredito { numero='%s' | cupo=$%,.2f | deuda=$%,.2f | disponible=$%,.2f | estado='%s' | cliente='%s' }",
                numeroCuenta, cupo, deuda, getCupoDisponible(), estado.getDescripcion(), cliente.getNombreCompleto()
        );
    }
}
