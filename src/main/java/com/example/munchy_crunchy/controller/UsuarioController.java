package com.example.munchy_crunchy.controller;

import java.util.List;
import java.util.Optional;

import com.example.munchy_crunchy.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.munchy_crunchy.model.Usuario;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getAllUsuario() {
        List<Usuario> usuario = usuarioService.getAll();
        if (usuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> createUsuario(Usuario usuario) {
        try {
            Usuario us = new Usuario();
            us = usuarioService.saveUsuario(usuario);
            return new ResponseEntity<Usuario>(us, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable int id) {
        try {
            usuarioService.deleteUsuario(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/update/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable int id, Usuario usuario) {
        try {
            Usuario us = new Usuario();
            Optional<Usuario> u = usuarioService.findUsuarioById(id);
            us = u.get();
            Usuario resultado = usuarioService.saveUsuario(us);
            return new ResponseEntity<Usuario>(resultado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
