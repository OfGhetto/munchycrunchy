package com.example.munchy_crunchy.service;

import java.util.List;
import java.util.Optional;

import com.example.munchy_crunchy.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.munchy_crunchy.model.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario saveUsuario(Usuario usuario) throws IllegalArgumentException {
        if (usuario == null) {
            throw new IllegalArgumentException();
        } else {
            try {
                return usuarioRepository.save(usuario);
            } catch (DataAccessException e) {
                throw new IllegalArgumentException();
            }
        }
    }

    public void deleteUsuario(Integer id) throws IllegalArgumentException {
        try {
            usuarioRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException();
        }

    }

    public Optional<Usuario> findUsuarioById(Integer id) throws IllegalArgumentException {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            return usuario;
        } catch (DataAccessException e) {
            throw new IllegalArgumentException();
        }

    }

}
