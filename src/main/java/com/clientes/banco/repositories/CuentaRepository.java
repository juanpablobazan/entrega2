package com.clientes.banco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.banco.entities.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
