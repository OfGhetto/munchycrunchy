package com.example.munchy_crunchy.service;



import com.example.munchy_crunchy.model.Ingredientes;
import com.example.munchy_crunchy.repository.IngredientesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientesServiceTest {
    @Mock
    private IngredientesRepository ingredientesRepository;

    @InjectMocks
    private IngredientesService ingredientesService;

    @Test
    void siSeInvocaGetAllIngredientesYExisteningredientesParaSerListadosRetornaUnaListaConLosIngredientes() {
        // Arrange
        ArrayList<Ingredientes> ingrediente = new ArrayList<Ingredientes>();
        List<Ingredientes> resultado;
        ingrediente.add(new Ingredientes(1, "Calabaza", "Frutos del maipo",3,"unidad"));
        ingrediente.add(new Ingredientes(2, "Lechuga","Auchan", 5, "unidad"));
        ingrediente.add(new Ingredientes(3, "Alga nori", "Gold",20, "unidad"));

        when(ingredientesRepository.findAll()).thenReturn(ingrediente);

        // Act
        resultado = ingredientesService.getAll();

        // Assert
        assertNotNull(resultado);
        assertAll("resultado",
                ()-> assertEquals(1, resultado.get(0).getId()),
                ()-> assertEquals("Calabaza", resultado.get(0).getNombre()),
                ()-> assertEquals("Frutos del maipo", resultado.get(0).getMarca()),
                ()-> assertEquals(3, resultado.get(0).getCantidad()),
                ()-> assertEquals("unidad", resultado.get(0).getUnidadMedida()),

                ()-> assertEquals(2, resultado.get(1).getId()),
                ()-> assertEquals("Lechuga", resultado.get(1).getNombre()),
                ()-> assertEquals("Auchan", resultado.get(1).getMarca()),
                ()-> assertEquals(5, resultado.get(1).getCantidad()),
                ()-> assertEquals("unidad", resultado.get(1).getUnidadMedida()),

                ()-> assertEquals(3, resultado.get(2).getId()),
                ()-> assertEquals("Alga nori", resultado.get(2).getNombre()),
                ()-> assertEquals("Gold", resultado.get(2).getMarca()),
                ()-> assertEquals(20, resultado.get(2).getCantidad()),
                ()-> assertEquals("unidad", resultado.get(2).getUnidadMedida())
        );
    }

    @Test
    void siSeInvocaGetAllIngredientesYNoExistenIngredienteParaSerListadosRetornaUnaListaVacia() {
        // Arrange
        ArrayList<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
        List<Ingredientes> resultado;

        when(ingredientesRepository.findAll()).thenReturn(ingredientes);

        // Act
        resultado = ingredientesService.getAll();

        // Assert
        assertNotNull(resultado);
        assertThat(resultado).isEqualTo(ingredientes);
    }

    @Test
    @Transactional
    void siSeInvocaSaveIngredienteLoDeberiaCrearingredienteYRetornarIngrediente() {
        // Arrange
        Ingredientes ingredientes = new Ingredientes(1, "Calabaza", "Frutos del maipo",3,"unidad");
        Ingredientes creado = new Ingredientes();

        when(ingredientesRepository.save(ingredientes)).thenReturn(ingredientes);

        // Act
        creado = ingredientesService.saveIngrediente(ingredientes);

        // Assert
        assertNotNull(creado);
        assertThat(creado.getId()).isEqualTo(1);
        assertThat(creado.getNombre()).isEqualTo("Calabaza");
        assertThat(creado.getMarca()).isEqualTo("Frutos del maipo");
        assertThat(creado.getCantidad()).isEqualTo(3);
        assertThat(creado.getUnidadMedida()).isEqualTo("unidad");

    }

    @Test
    @Transactional
    void siSeInvocaSaveIngredienteConIngredienteNuloSeLanzaUnaExcepcion() {
        Ingredientes ingredientes = null;

        assertThrows(IllegalArgumentException.class,()->ingredientesService.saveIngrediente(ingredientes));
    }

    @Test
    @Transactional
    void siSeInvocaDeleteIngredienteEsteLoBorra() throws Exception {
        // Arrange
        int id = 1;

        // Act
        ingredientesService.deleteIngrediente(id);

        // Assert
        verify(ingredientesRepository, times(1)).deleteById(1);
    }

    @Test
    @Transactional
    void siSeInvocaDeleteIngredienteYFallaLanzaUnaExcepcion() throws Exception {
        // Arrange
        Integer id = null;

        doThrow(DataRetrievalFailureException.class).when(ingredientesRepository).deleteById(id);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,()->ingredientesService.deleteIngrediente(id));
    }

    @Test
    @Transactional
    void siSeInvocaFindIngredienteByIdRetornaElIngrediente() {
        // Arrange
        Ingredientes ingredientes= new Ingredientes(1, "Calabaza", "Frutos del maipo",3,"unidad");
        Integer id = 1;

        when(ingredientesRepository.findById(1)).thenReturn(Optional.of(ingredientes));

        // Act
        Optional<Ingredientes> resultado = ingredientesService.findIngredienteById(id);

        // Assert
        assertNotNull(resultado);
        assertThat(resultado.get().getId()).isEqualTo(1);
        assertThat(resultado.get().getNombre()).isEqualTo("Calabaza");
        assertThat(resultado.get().getMarca()).isEqualTo("Frutos del maipo");
        assertThat(resultado.get().getCantidad()).isEqualTo(3);
        assertThat(resultado.get().getUnidadMedida()).isEqualTo("unidad");
    }

    @Test
    @Transactional
    void siSeInvocaFindIngredienteByIdYElIdEsNuloLanzaUnaExcepcion() {
        // Arrange
        Integer id = null;

        doThrow(DataRetrievalFailureException.class).when(ingredientesRepository).findById(id);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,()->ingredientesService.findIngredienteById(id));
    }


    @Test
    @Transactional
    void siSeInvocasaveIngredienteYseEditaElIngredienteDeberiaRetornarElEditado() {
        // Arrange
        Ingredientes ingredientes = new Ingredientes(1, "Calabaza", "Frutos del maipo",3,"unidad");
        Ingredientes creado = new Ingredientes();

        when(ingredientesRepository.save(ingredientes)).thenReturn(ingredientes);

        // Act
        ingredientes.setNombre("Calabazin");
        creado = ingredientesService.saveIngrediente(ingredientes);

        // Assert
        assertNotNull(creado);
        assertThat(creado.getId()).isEqualTo(1);
        assertThat(creado.getNombre()).isEqualTo("Calabazin");
        assertThat(creado.getMarca()).isEqualTo("Frutos del maipo");
        assertThat(creado.getCantidad()).isEqualTo(3);
        assertThat(creado.getUnidadMedida()).isEqualTo("unidad");
    }
}


