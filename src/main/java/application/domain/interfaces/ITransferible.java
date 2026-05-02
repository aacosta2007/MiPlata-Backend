package application.domain.interfaces;

import application.domain.Cuenta;

public interface ITransferible {

    void transferir(Cuenta destino, double monto);

    boolean validarDestino(Cuenta c);
}
