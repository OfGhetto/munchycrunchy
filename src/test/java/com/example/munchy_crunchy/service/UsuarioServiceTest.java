package com.example.munchy_crunchy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.example.munchy_crunchy.repository.UsuarioRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.munchy_crunchy.fixtures.UsuarioFixture;
import com.example.munchy_crunchy.model.Usuario;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void siSeInvocaGetAllUsuarioYExistenUsuarioParaSerListadosRetornaUnaListaConLosUsuario() {
        // Arrange
        List<Usuario> resultado;
        when(usuarioRepository.findAll()).thenReturn(UsuarioFixture.obtenerUsuariosFixtures());
        // Act
        resultado = usuarioService.getAll();
        // Assert
        assertNotNull(resultado);
        assertAll("resultado", () -> assertEquals(1, resultado.get(0).getId()),
                () -> assertEquals("Felipito12", resultado.get(0).getUsername()),
                () -> assertEquals("Felipito12@correo.cl", resultado.get(0).getEmail()),
                () -> assertEquals("segura9283", resultado.get(0).getPassword()),
                () -> assertEquals("Felipe Bustos", resultado.get(0).getNombre()),
                () -> assertEquals("194758695", resultado.get(0).getRut()),
                () -> assertEquals("usuario", resultado.get(0).getRole()),

                () -> assertEquals(2, resultado.get(1).getId()),
                () -> assertEquals("josefSa", resultado.get(1).getUsername()),
                () -> assertEquals("josefa@correo.cl", resultado.get(1).getEmail()),
                () -> assertEquals("9fae2345r", resultado.get(1).getPassword()),
                () -> assertEquals("Josefa Fernandez", resultado.get(1).getNombre()),
                () -> assertEquals("184569872", resultado.get(1).getRut()),
                () -> assertEquals("usuario", resultado.get(1).getRole()),

                () -> assertEquals(3, resultado.get(2).getId()),
                () -> assertEquals("MatSand", resultado.get(2).getUsername()),
                () -> assertEquals("matiassand1965@correo.cl", resultado.get(2).getEmail()),
                () -> assertEquals("sandMat65", resultado.get(2).getPassword()),
                () -> assertEquals("Matias Sandoval", resultado.get(2).getNombre()),
                () -> assertEquals("102458963", resultado.get(2).getRut()),
                () -> assertEquals("usuario", resultado.get(2).getRole()));
    }

    @Test
    void siSeInvocaGetAllUsuarioYNoExistenUsuarioParaSerListadosRetornaUnaListaVacia() {
        // Arrange
        ArrayList<Usuario> usuario = new ArrayList<Usuario>();
        List<Usuario> resultado;
        when(usuarioRepository.findAll()).thenReturn(usuario);
        // Act
        resultado = usuarioService.getAll();
        // Assert
        assertNotNull(resultado);
        assertThat(resultado).isEqualTo(usuario);
    }

    @Test
    @Transactional
    void siSeInvocaSaveUsuarioLoDeberiaCrearUsuarioYRetornarUsuario() {
        // Arrange
        Usuario usuario = new Usuario(1, "Felipito12", "Felipito12@correo.cl", "segura9283", "Felipe Bustos",
                "98765432", "usuario");
        Usuario creado = new Usuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        // Act
        creado = usuarioService.saveUsuario(usuario);
        // Assert
        assertNotNull(creado);
        assertThat(creado.getId()).isEqualTo(1);
        assertThat(creado.getUsername()).isEqualTo("Felipito12");
        assertThat(creado.getEmail()).isEqualTo("Felipito12@correo.cl");
        assertThat(creado.getPassword()).isEqualTo("segura9283");
        assertThat(creado.getNombre()).isEqualTo("Felipe Bustos");
        assertThat(creado.getRut()).isEqualTo("98765432");
        assertThat(creado.getRole()).isEqualTo("usuario");
    }

    @Test
    @Transactional
    void siSeInvocaSaveUsuarioConUsuarioNuloSeLanzaUnaExcepcion() {
        Usuario usuario = null;
        assertThrows(IllegalArgumentException.class, () -> usuarioService.saveUsuario(usuario));
    }

    @Test
    @Transactional
    void siSeInvocaDeleteUsuarioEsteLoBorra() throws Exception {
        // Arrange
        int id = 1;
        // Act
        usuarioService.deleteUsuario(id);
        // Assert
        verify(usuarioRepository, times(1)).deleteById(1);
    }

}
