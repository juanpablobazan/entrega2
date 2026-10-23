package com.clientes.banco.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.banco.entities.Usuario;
import com.clientes.banco.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET http://localhost:8080/usuarios
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    // GET http://localhost:8080/usuarios/1
    @GetMapping("/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    // POST http://localhost:8080/usuarios/guardar
    @PostMapping("/guardar")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    // DELETE http://localhost:8080/usuarios/eliminar/1
    @DeleteMapping("/eliminar/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    // PUT http://localhost:8080/usuarios/editar/1
    @PutMapping("/editar/{id}")
    public Usuario editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.editarUsuario(usuario);
    }
}
