package application.domain;

import application.domain.enums.TipoMovimiento;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movimiento {

    private int id;
    private LocalDateTime fechaHora;
    private TipoMovimiento tipo;
    private double valor;
    private double saldoPosterior;
    private String descripcion;


    public Movimiento() {}

    public Movimiento(int id, LocalDateTime fechaHora, TipoMovimiento tipo,
                      double valor, double saldoPosterior, String descripcion) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.valor = valor;
        this.saldoPosterior = saldoPosterior;
        this.descripcion = descripcion;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getSaldoPosterior() {
        return saldoPosterior;
    }

    public void setSaldoPosterior(double saldoPosterior) {
        this.saldoPosterior = saldoPosterior;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getFechaHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHora != null ? fechaHora.format(formatter) : "Sin fecha";
    }

    @Override
    public String toString() {
        return String.format("[%s] %-28s | Valor: $%,-12.2f | Saldo posterior: $%,.2f | %s",
                getFechaHoraFormateada(),
                tipo.getDescripcion(),
                valor,
                saldoPosterior,
                descripcion);
    }
}
