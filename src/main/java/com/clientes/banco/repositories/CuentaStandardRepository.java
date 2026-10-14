package com.clientes.banco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.banco.entities.CuentaStandard;

public interface CuentaStandardRepository extends JpaRepository<CuentaStandard, Long> {
}
