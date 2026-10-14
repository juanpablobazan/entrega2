package com.clientes.banco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clientes.banco.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
