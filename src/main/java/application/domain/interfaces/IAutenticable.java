package application.domain.interfaces;

public interface IAutenticable {

    boolean autenticar(String usuario, String contrasena);

    void cerrarSesion();

    void cambiarContrasena(String antigua, String nueva);
}
