package com.clientes.banco.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;

import com.clientes.banco.entities.Usuario;
import com.clientes.banco.exceptions.ErrorBaseDatosException;
import com.clientes.banco.exceptions.RecursoNoEncontradoException;
import com.clientes.banco.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void listarDevuelveLosUsuariosDelRepositorio() {
        List<Usuario> usuarios = List.of(new Usuario(1L, "Ana", "Perez", "123"));
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        assertSame(usuarios, usuarioService.listar());
    }

    @Test
    void buscarUsuarioPorIdLanzaExcepcionSiNoExiste() {
        when(usuarioRepository.findById(10L)).thenReturn(Optional.empty());

        RecursoNoEncontradoException ex = assertThrows(RecursoNoEncontradoException.class,
                () -> usuarioService.buscarUsuarioPorId(10L));

        assertEquals("No existe el usuario con id 10", ex.getMessage());
    }

    @Test
    void editarUsuarioUsaElIdDeLaRuta() {
        Usuario usuario = new Usuario(99L, "Ana", "Perez", "123");
        when(usuarioRepository.existsById(5L)).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.editarUsuario(5L, usuario);

        assertEquals(5L, resultado.getId());
    }

    @Test
    void convierteErrorDelRepositorioEnExcepcionPropia() {
        DataRetrievalFailureException fallo = new DataRetrievalFailureException("fallo");
        when(usuarioRepository.findAll()).thenThrow(fallo);

        ErrorBaseDatosException ex = assertThrows(ErrorBaseDatosException.class, () -> usuarioService.listar());

        assertSame(fallo, ex.getCause());
    }
}
