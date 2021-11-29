package com.example.munchy_crunchy.controller;


import com.example.munchy_crunchy.model.Categoria;
import com.example.munchy_crunchy.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoriaControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CategoriaService categoriaService;
    private JacksonTester<List<Categoria>> jsonListaCategorias;
    private JacksonTester<Categoria> jsonCategoria;

    @Autowired
    private TestRestTemplate restTemplate;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
    }

    @Test
    void siSeInvocaGetAllCategoriaYExistenCategoriasParaSerListadasRetornarListaConCategorias() throws Exception {
        //Given
        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1, "Ensaladas", "Gran variedad de ensaladas"));
        categorias.add(new Categoria(2,"Bebestibles", "Gran variedad de bebestibles"));
        categorias.add(new Categoria(3, "Sandwiches", "Gran variedad de sandwiches veganos"));
        given(categoriaService.getAll()).willReturn(categorias);

        //When
        MockHttpServletResponse response = mockMvc
                .perform(get("/categoria/all").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListaCategorias.write(categorias).getJson());
    }

    @Test
    void siSeInvocaGetAllCategoriaYNoExistenCategoriasParaSerListadasRetornaNO_CONTENTStatus() throws Exception{
        //Given
        ArrayList<Categoria> categorias = new ArrayList<>();
        given(categoriaService.getAll()).willReturn(categorias);

        //When
        MockHttpServletResponse response = mockMvc
                .perform(get("/categoria/all").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString().isEmpty());
    }

    @Test
    void siSeInvocaCreateCategoriaYEstaSePuedeCrearSeCreaLaCategoria() throws Exception {
        //Given
        Categoria categoria = new Categoria(1, "Ensaladas", "Gran variedad de ensaladas");
        given(categoriaService.saveCategoria(ArgumentMatchers.any(Categoria.class))).willReturn(categoria);
        ObjectMapper mapper = new ObjectMapper();
        String newCategoria = mapper.writeValueAsString(categoria);

        //When
        this.mockMvc.perform(post("/categoria/create").content(newCategoria)
                        .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                // Then
                .andExpect(status().isCreated()).andReturn().equals(categoria);
    }

    @Test
    void siSeInvocaCreateCategoriaYEstaSePuedeCrearSeCreaLaCategoriaYRetornaCreatedStatusCode() throws Exception {
        //Given
        Categoria categoria = new Categoria(1, "Ensaladas", "Gran variedad de ensaladas");
        given(categoriaService.saveCategoria(ArgumentMatchers.any(Categoria.class))).willReturn(categoria);

        //When
        MockHttpServletResponse response = mockMvc.
                perform(post("/categoria/create").contentType(MediaType.APPLICATION_JSON).content(jsonCategoria.write(categoria).getJson()))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCategoria.write(categoria).getJson());
    }

    @Test
    void siSeInvocaCreateCategoriaYEstaEsNulaNoCreaLaCategoriaYRetornaUNPROCESSABLEENTITYStatusCode() throws Exception{
        //Given
        Mockito.doThrow(IllegalArgumentException.class).when(categoriaService)
                .saveCategoria(ArgumentMatchers.any(Categoria.class));

        //When
        MockHttpServletResponse response = mockMvc.
                perform(post("/categoria/create").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void siSeInvocaDeleteCategoriaRetornaStatusCodeOK() throws Exception {
        //Given
        doNothing().when(categoriaService).deleteCategoria(1);

        //When
        MockHttpServletResponse response = mockMvc.
                perform(get("/categoria/delete/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void siSeInvocaDeleteCategoriaSinIdRetornaNOT_FOUNDStatusCode() throws Exception {
        //When
        MockHttpServletResponse response = mockMvc.
                perform(get("/categoria/delete/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        
        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void siSeInvocaUpdateCategoriaYEstaNoEsNulaRetornaStatusCodeOK() throws Exception {
        //Given
        Categoria categoria = new Categoria(1, "Ensaladas", "Gran variedad de ensaladas");
        given(categoriaService.findCategoriaById(1)).willReturn(Optional.of(categoria));

        categoria.setNombre("Para Llevar");
        categoria.setDescripcion("Gran variedad de productos para llevar");

        given(categoriaService.saveCategoria(ArgumentMatchers.any(Categoria.class))).willReturn(categoria);

        //When

        MockHttpServletResponse response = mockMvc.
                perform(get("/categoria/update/1")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonCategoria.write(categoria).getJson()))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCategoria.write(categoria).getJson());
    }

    @Test
    void siSeInvocaUpdateCategoriaSinIdRetornaNOT_FOUNDStatusCode() throws Exception {
        //When
        MockHttpServletResponse response = mockMvc
                .perform(get("/categoria/update/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
