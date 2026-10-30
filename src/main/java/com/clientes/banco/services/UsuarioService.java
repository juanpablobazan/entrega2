package com.clientes.banco.services;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.clientes.banco.entities.Usuario;
import com.clientes.banco.exceptions.ErrorBaseDatosException;
import com.clientes.banco.exceptions.RecursoNoEncontradoException;
import com.clientes.banco.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        try {
            return usuarioRepository.findAll();
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al listar los usuarios", ex);
        }
    }

    public Usuario buscarUsuarioPorId(Long id) {
        try {
            return usuarioRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe el usuario con id " + id));
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al buscar el usuario con id " + id, ex);
        }
    }

    public Usuario guardarUsuario(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al guardar el usuario", ex);
        }
    }

    public void eliminarUsuario(Long id) {
        try {
            if (!usuarioRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe el usuario con id " + id);
            }
            usuarioRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al eliminar el usuario con id " + id, ex);
        }
    }

    public Usuario editarUsuario(Long id, Usuario usuario) {
        try {
            if (!usuarioRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("No existe el usuario con id " + id);
            }
            usuario.setId(id);
            return usuarioRepository.save(usuario);
        } catch (DataAccessException ex) {
            throw new ErrorBaseDatosException("Error al editar el usuario con id " + id, ex);
        }
    }
}
