package com.example.munchy_crunchy.fixtures;

import com.example.munchy_crunchy.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioFixture {
        public static List<Usuario> obtenerUsuariosFixtures() {

                ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
                usuarios.add(new Usuario(1, "Felipito12", "Felipito12@correo.cl", "segura9283", "Felipe Bustos",
                                "194758695", "usuario"));
                usuarios.add(new Usuario(2, "josefSa", "josefa@correo.cl", "9fae2345r", "Josefa Fernandez", "184569872",
                                "usuario"));
                usuarios.add(new Usuario(3, "MatSand", "matiassand1965@correo.cl", "sandMat65", "Matias Sandoval",
                                "102458963", "usuario"));

                return usuarios;
        }

}
