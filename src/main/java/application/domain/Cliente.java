package application.domain;

import application.domain.interfaces.IAutenticable;

public class Cliente implements IAutenticable {

    private static final int MAX_INTENTOS = 3;

    private int id;
    private String identificacion;
    private String nombreCompleto;
    private String celular;
    private String usuario;
    private String contrasena;
    private int intentosFallidos;
    private boolean bloqueado;


    public Cliente() {}

    public Cliente(int id, String identificacion, String nombreCompleto,
                   String celular, String usuario, String contrasena) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.intentosFallidos = 0;
        this.bloqueado = false;
    }


    @Override
    public boolean autenticar(String usuario, String contrasena) {
        if (bloqueado) {
            return false;
        }

        boolean credencialesCorrectas = this.usuario.equals(usuario)
                && this.contrasena.equals(contrasena);

        if (credencialesCorrectas) {
            resetearIntentos();
            return true;
        } else {
            incrementarIntentos();
            return false;
        }
    }

    @Override
    public void cerrarSesion() {
    }

    @Override
    public void cambiarContrasena(String antigua, String nueva) {
        if (!this.contrasena.equals(antigua)) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta.");
        }
        if (nueva == null || nueva.isBlank()) {
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía.");
        }
        if (nueva.length() < 6) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres.");
        }
        this.contrasena = nueva;
    }


    public void incrementarIntentos() {
        this.intentosFallidos++;
        if (this.intentosFallidos >= MAX_INTENTOS) {
            this.bloqueado = true;
        }
    }

    public void resetearIntentos() {
        this.intentosFallidos = 0;
    }

    public void editarPerfil(String nombreCompleto, String celular) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío.");
        }
        this.nombreCompleto = nombreCompleto;
        this.celular = celular;
    }

    public void desbloquear() {
        this.bloqueado = false;
        this.intentosFallidos = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }


    @Override
    public String toString() {
        return String.format(
                "Cliente { id=%d | identificacion='%s' | nombre='%s' | celular='%s' | usuario='%s' | bloqueado=%s }",
                id, identificacion, nombreCompleto, celular, usuario, bloqueado ? "SÍ" : "NO"
        );
    }
}
