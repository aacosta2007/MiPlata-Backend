package application.domain.enums;

public enum TipoMovimiento {

    CONSIGNACION("Consignación"),
    RETIRO("Retiro"),
    TRANSFERENCIA_OUT("Transferencia enviada"),
    TRANSFERENCIA_IN("Transferencia recibida"),
    COMPRA_TC("Compra con tarjeta de crédito"),
    PAGO_TC("Pago de tarjeta de crédito");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
