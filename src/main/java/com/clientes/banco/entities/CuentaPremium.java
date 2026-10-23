package com.clientes.banco.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuenta_premium")
public class CuentaPremium extends Cuenta {

    
    @Column(name = "puntos_premium")
    private int puntosPremium;

    @Column(name = "porcentaje_descuento")
    private int porcentajeDescuento;

    public CuentaPremium() {
    }

    public CuentaPremium(Long id, String numeroCuenta, Float saldo, Usuario cliente, int puntosPremium, int porcentajeDescuento) {
        super(id, numeroCuenta, saldo, cliente);
        this.puntosPremium = puntosPremium;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public int getPuntosPremium() {
        return puntosPremium;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPuntosPremium(int puntosPremium) {
        this.puntosPremium = puntosPremium;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}
