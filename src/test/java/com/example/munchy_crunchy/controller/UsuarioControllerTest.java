package com.example.munchy_crunchy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.munchy_crunchy.model.Usuario;

import com.example.munchy_crunchy.controller.UsuarioController;
import com.example.munchy_crunchy.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

        private MockMvc mockMvc;

        @Mock
        private UsuarioService usuarioService;
        private JacksonTester<Usuario> jsonUsuario;
        private JacksonTester<List<Usuario>> jsonListaUsuario;

        @InjectMocks
        private UsuarioController usuarioController;

        @BeforeEach
        void setup() {
                JacksonTester.initFields(this, new ObjectMapper());
                mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        }

        @Test
        void siSeInvocaGetAllUsuarioYexistenUsuarioParaSerListadosDebeRetornarListaConLosUsuarioYOkStatusCode()
                        throws Exception {
                // Given
                ArrayList<Usuario> usuario = new ArrayList<Usuario>();
                usuario.add(new Usuario(1, "Felipito12", "Felipito12@correo.cl", "segura9283", "Felipe Bustos",
                                "194758695", "usuario"));
                usuario.add(new Usuario(2, "josefSa", "josefa@correo.cl", "9fae2345r", "Josefa Fernandez", "184569872",
                                "usuario"));
                usuario.add(new Usuario(3, "MatSand", "matiassand1965@correo.cl", "sandMat65", "Matias Sandoval",
                                "102458963", "usuario"));
                given(usuarioService.getAll()).willReturn(usuario);
                // When
                MockHttpServletResponse response = mockMvc
                                .perform(get("/usuario/all").accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();
                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).isEqualTo(jsonListaUsuario.write(usuario).getJson());
        }

        @Test
        void siSeInvocaGetAllUsuarioYNoexistenUsuarioParaSerListadosRetornaNO_CONTENTStatus() throws Exception {
                // Given
                ArrayList<Usuario> usuario = new ArrayList<Usuario>();
                given(usuarioService.getAll()).willReturn(usuario);
                // When
                MockHttpServletResponse response = mockMvc
                                .perform(get("/usuario/all").accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();
                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
                assertThat(response.getContentAsString()).isEmpty();
        }

        @Test
        void siSeInvocaCreateUsuarioYEsteSePuedeCrearSeCreaElUsuario() throws Exception {
                // Given
                Usuario usuario = new Usuario(1, "Felipito12", "Felipito12@correo.cl", "segura9283", "Felipe Bustos",
                                "194758695", "usuario");
                given(usuarioService.saveUsuario(ArgumentMatchers.any(Usuario.class))).willReturn(usuario);
                ObjectMapper mapper = new ObjectMapper();
                String newDetallePedidoAsJSON = mapper.writeValueAsString(usuario);
                // When
                this.mockMvc.perform(post("/usuario/create").content(newDetallePedidoAsJSON)
                                .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                                // Then
                                .andExpect(status().isCreated()).andReturn().equals(usuario);
        }

        @Test
        void siSeInvocaCreateUsuarioYEsteSePuedeCrearSeCreaElUsuarioYRetornaCreatedStatusCode() throws Exception {
                // Given
                Usuario usuario = new Usuario(1, "Felipito12", "Felipito12@correo.cl", "segura9283", "Felipe Bustos",
                                "194758695", "usuario");
                given(usuarioService.saveUsuario(ArgumentMatchers.any(Usuario.class))).willReturn(usuario);
                // When
                MockHttpServletResponse response = mockMvc.perform(post("/usuario/create")
                                .contentType(MediaType.APPLICATION_JSON).content(jsonUsuario.write(usuario).getJson()))
                                .andReturn().getResponse();
                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
                assertThat(response.getContentAsString()).isEqualTo(jsonUsuario.write(usuario).getJson());
        }

        @Test
        void siSeInvocaCreateUsuarioYEsteEsNuloNoCreaElUsuarioYRetornaUNPROCESSABLEENTITYStatusCode() throws Exception {
                // Given
                Mockito.doThrow(IllegalArgumentException.class).when(usuarioService)
                                .saveUsuario(ArgumentMatchers.any(Usuario.class));

                // When
                MockHttpServletResponse response = mockMvc.perform(
                                post("/usuario/create").accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();
                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }

        @Test
        void siSeInvocaDeleteUsuarioRetornaOKStatusCode() throws Exception {
                //
                doNothing().when(usuarioService).deleteUsuario(1);

                // When
                MockHttpServletResponse response = mockMvc.perform(
                                get("/usuario/delete/1").accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void siSeInvocaDeleteUsuarioSinIdRetornaNOTFOUNDStatusCode() throws Exception {
                // When
                MockHttpServletResponse response = mockMvc.perform(
                                get("/usuario/delete/").accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();

                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void siSeInvocaUpdateUsuarioYEsteNoEsNuloRetornaOkStatusCode() throws Exception {
                // Given
                Usuario usuario = new Usuario(1, "Felipito12", "Felipito12@correo.cl", "segura9283", "Felipe Bustos",
                                "194758695", "usuario");

                given(usuarioService.findUsuarioById(1)).willReturn(Optional.of(usuario));

                usuario.setPassword("Bustos24F");
                usuario.setUsername("BusFel12");

                given(usuarioService.saveUsuario(ArgumentMatchers.any(Usuario.class))).willReturn(usuario);

                // When
                MockHttpServletResponse response = mockMvc.perform(get("/usuario/update/1")
                                .contentType(MediaType.APPLICATION_JSON).content(jsonUsuario.write(usuario).getJson()))
                                .andReturn().getResponse();
                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
                assertThat(response.getContentAsString()).isEqualTo(jsonUsuario.write(usuario).getJson());

        }

        @Test
        void siSeInvocaUpdateUsuarioSinIdRetornaNOTFOUNDStatusCode() throws Exception {
                // Given When
                MockHttpServletResponse response = mockMvc.perform(
                                get("/usuario/update/").accept(MediaType.APPLICATION_JSON))
                                .andReturn().getResponse();
                // Then
                assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        }

}
