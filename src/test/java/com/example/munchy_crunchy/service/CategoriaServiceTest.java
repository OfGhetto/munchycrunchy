package com.example.munchy_crunchy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;

import com.example.munchy_crunchy.model.Categoria;
import com.example.munchy_crunchy.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    void siSeInvocaGetAllCategoriasParaSerListadasRetornarUnaListaConLasCategorias() {
        //Arrange
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        List<Categoria> resultado;
        categorias.add(new Categoria(1, "Ensaladas", "Gran variedad de ensaladas"));
        categorias.add(new Categoria(2, "Bebestibles", "Gran variedad de bebestibles"));
        categorias.add(new Categoria(3, "Sandwiches", "Gran variedad de sandwiches veganos"));

        when(categoriaRepository.findAll()).thenReturn(categorias);

        //Act
        resultado = categoriaService.getAll();

        //Assert
        assertNotNull(resultado);
        assertAll("resultado",
                ()-> assertEquals(1, resultado.get(0).getId()),
                ()-> assertEquals("Ensaladas", resultado.get(0).getNombre()),
                ()-> assertEquals("Gran variedad de ensaladas", resultado.get(0).getDescripcion()),

                ()-> assertEquals(2, resultado.get(1).getId()),
                ()-> assertEquals("Bebestibles", resultado.get(1).getNombre()),
                ()-> assertEquals("Gran variedad de bebestibles", resultado.get(1).getDescripcion()),

                ()-> assertEquals(3, resultado.get(2).getId()),
                ()-> assertEquals("Sandwiches", resultado.get(2).getNombre()),
                ()-> assertEquals("Gran variedad de sandwiches veganos", resultado.get(2).getDescripcion())
        );
    }

    @Test
    void siSeInvocaGetAllCategoriasYNoExistenCategoriasParaSerListadasRetornarUnaListaVacia() {
        //Arrange
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        List<Categoria> resultado;

        when(categoriaRepository.findAll()).thenReturn(categorias);

        //Act
        resultado = categoriaService.getAll();

        //Assert
        assertNotNull(resultado);
        assertThat(resultado).isEqualTo(categorias);
    }

    @Test
    @Transactional
    void siSeInvocaSaveCategoriaDeberiaCrearCategoriaYRetornarCategoria() {
        //Arrange
        Categoria categoria = new Categoria(1,"Ensaladas", "Gran variedad de ensaladas");
        Categoria creada = new Categoria();

        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        //Act
        creada = categoriaService.saveCategoria(categoria);

        //Assert
        assertNotNull(creada);
        assertThat(creada.getId()).isEqualTo(1);
        assertThat(creada.getNombre()).isEqualTo("Ensaladas");
        assertThat(creada.getDescripcion()).isEqualTo("Gran variedad de ensaladas");
    }

    @Test
    @Transactional
    void siSeInvocaSaveCategoriaConCategoriaNulaSeLanzaUnaExcepcion() {
        Categoria categoria = null;
        assertThrows(IllegalArgumentException.class, ()->categoriaService.saveCategoria(categoria));
    }

    @Test
    @Transactional
    void siSeInvocaDeleteCategoriaEstaSeElimina() throws Exception {
        //Arrange
        int id = 1;

        //Act
        categoriaService.deleteCategoria(id);

        //Assert
        verify(categoriaRepository, times(1)).deleteById(1);
    }

    @Test
    @Transactional
    void siSeInvocaDeleteCategoriaYEsteFallaSeLanzaUnaExcepcion() throws Exception {
        //Arrange
        Integer id = null;
        doThrow(DataRetrievalFailureException.class).when(categoriaRepository).deleteById(id);

        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()->categoriaService.deleteCategoria(id));
    }

    @Test
    @Transactional
    void siSeInvocaFindCategoriaByIdRetornaLaCategoria() {
        //Arrange
        Categoria categoria = new Categoria(1,"Ensaladas", "Gran variedad de ensaladas");
        Integer id = 1;
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));

        //Act
        Optional<Categoria> resultado =categoriaService.findCategoriaById(id);

        //Assert
        assertNotNull(resultado);
        assertThat(resultado.get().getId()).isEqualTo(1);
        assertThat(resultado.get().getNombre()).isEqualTo("Ensaladas");
        assertThat(resultado.get().getDescripcion()).isEqualTo("Gran variedad de ensaladas");
    }

    @Test
    @Transactional
    void siSeInvocaFindCategoriaByIdYElIdEsNuloLanzaUnaExcepcion() throws Exception {
        //Arrange
        Integer id = 1;
        doThrow(DataRetrievalFailureException.class).when(categoriaRepository).findById(id);

        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()->categoriaService.findCategoriaById(id));
    }

    @Test
    @Transactional
    void siSeInvocaSaveCategoriaYEstaSeEditaLaCategoriaDebeRetornarYaEditada() {
        //Arrange
        Categoria categoria = new Categoria(1,"Ensaladas", "Gran variedad de ensaladas");
        Categoria creada = new Categoria();
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        //Act
        categoria.setDescripcion("Gran variedad de ensaladas light");
        creada = categoriaService.saveCategoria(categoria);

        //Assert
        assertNotNull(creada);
        assertThat(creada.getId()).isEqualTo(1);
        assertThat(creada.getNombre()).isEqualTo("Ensaladas");
        assertThat(creada.getDescripcion()).isEqualTo("Gran variedad de ensaladas light");
    }
}