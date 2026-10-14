package com.clientes.banco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.banco.entities.CuentaPremium;

public interface CuentaPremiumRepository extends JpaRepository<CuentaPremium, Long> {
}
