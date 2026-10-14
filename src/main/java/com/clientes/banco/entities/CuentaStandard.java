package com.clientes.banco.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STANDARD")
public class CuentaStandard extends Cuenta {
    @Column(name = "puntos_acumulados")
    private int puntosAcumulados;

    public CuentaStandard() {
    }

    public CuentaStandard(Long id, String numeroCuenta, Float saldo, Usuario cliente, int puntosAcumulados) {
        super(id, numeroCuenta, saldo, cliente);
        this.puntosAcumulados = puntosAcumulados;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }
}
