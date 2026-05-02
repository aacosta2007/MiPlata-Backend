package application.domain.enums;

public enum EstadoCuenta {

    ACTIVA("Activa"),
    INACTIVA("Inactiva"),
    BLOQUEADA("Bloqueada"),
    CERRADA("Cerrada");

    private final String descripcion;

    EstadoCuenta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
