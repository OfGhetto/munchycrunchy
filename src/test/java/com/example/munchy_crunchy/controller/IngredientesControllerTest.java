package com.example.munchy_crunchy.controller;

import com.example.munchy_crunchy.model.Ingredientes;
import com.example.munchy_crunchy.service.IngredientesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class IngredientesControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IngredientesService ingredientesService;

    private JacksonTester<List<Ingredientes>> jsonListaIngredientes;

    private JacksonTester<Ingredientes> jsonIngredientes;

    @InjectMocks
    private IngredientesController ingredientesController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientesController).build();
    }

    @Test
    void siSeInvocaGetAllIngredientesYexistenIngredientesParaSerListadosDebeRetornarListaConLosIngredienteYOkStatusCode()
            throws Exception {
        // Given
        ArrayList<Ingredientes> ingrediente = new ArrayList<Ingredientes>();
        ingrediente.add(new Ingredientes(1, "Calabaza", "Frutos del maipo", 3, "unidad"));
        ingrediente.add(new Ingredientes(2, "Lechuga", "Auchan", 5, "unidad"));
        ingrediente.add(new Ingredientes(3, "Alga nori", "Gold", 20, "unidad"));

        given(ingredientesService.getAll()).willReturn(ingrediente);

        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/ingredientes/all").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListaIngredientes.write(ingrediente).getJson());

    }

    @Test
    void siSeInvocaGetAllIngredientesYNoexistenIngredienteParaSerListadosRetornaNO_CONTENTStatus() throws Exception {
        // Given
        ArrayList<Ingredientes> ingrediente = new ArrayList<Ingredientes>();

        given(ingredientesService.getAll()).willReturn(ingrediente);

        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/ingredientes/all").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    void siSeInvocaCreateIngredientesYEsteSePuedeCrearSeCreaElIngrediente() throws Exception {
        // Given
        Ingredientes ingrediente = new Ingredientes(1, "Calabaza", "Frutos del maipo", 3, "unidad");

        given(ingredientesService.saveIngrediente(ArgumentMatchers.any(Ingredientes.class))).willReturn(ingrediente);
        ObjectMapper mapper = new ObjectMapper();

        String newDetalleAsJSON = mapper.writeValueAsString(ingrediente);

        // When
        this.mockMvc
                .perform(post("/ingredientes/create").content(newDetalleAsJSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))

                // Then
                .andExpect(status().isCreated()).andReturn().equals(ingrediente);

    }

    @Test
    void siSeInvocaCreateIngredientesYEsteSePuedeCrearSeCreaElIngredienteYRetornaCreatedStatusCode() throws Exception {
        // Given
        Ingredientes ingrediente = new Ingredientes(1, "Calabaza", "Frutos del maipo", 3, "unidad");

        given(ingredientesService.saveIngrediente(ArgumentMatchers.any(Ingredientes.class))).willReturn(ingrediente);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/ingredientes/create")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonIngredientes.write(ingrediente).getJson()))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonIngredientes.write(ingrediente).getJson());

    }

    @Test
    void siSeInvocaCreateIngredientesYEsteEsNuloNoCreaElIngredienteYRetornaUNPROCESSABLEENTITYStatusCode()
            throws Exception {
        // Given
        Mockito.doThrow(IllegalArgumentException.class).when(ingredientesService)
                .saveIngrediente(ArgumentMatchers.any(Ingredientes.class));

        // When
        MockHttpServletResponse response = mockMvc
                .perform(post("/ingredientes/create").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void siSeInvocaDeleteIngredientesRetornaOKStatusCode() throws Exception {
        //
        doNothing().when(ingredientesService).deleteIngrediente(1);

        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/ingredientes/delete/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void siSeInvocaDeleteIngredientesSinIdRetornaNOTFOUNDStatusCode() throws Exception {
        // When
        MockHttpServletResponse response = mockMvc
                .perform(get("/ingredientes/delete/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void siSeInvocaUpdateIngredientesYEsteNoEsNuloRetornaOkStatusCode() throws Exception {
        // Given
        Ingredientes ingrediente = new Ingredientes(1, "Calabaza", "Frutos del maipo", 3, "unidad");

        given(ingredientesService.findIngredienteById(1)).willReturn(Optional.of(ingrediente));

        ingrediente.setNombre("Calabazin");
        ingrediente.setMarca("Vega tajo");
        ingrediente.setCantidad(5);
        ingrediente.setUnidadMedida("unidades");

        given(ingredientesService.saveIngrediente(ArgumentMatchers.any(Ingredientes.class))).willReturn(ingrediente);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/ingredientes/update/1")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonIngredientes.write(ingrediente).getJson()))
                .andReturn().getResponse();
        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonIngredientes.write(ingrediente).getJson());

    }

    @Test
    void siSeInvocaUpdateIngredientesSinIdRetornaNOTFOUNDStatusCode() throws Exception {
        // Given When
        MockHttpServletResponse response = mockMvc
                .perform(get("/ingredientes/update/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

}
