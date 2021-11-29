package com.example.munchy_crunchy.service;

import com.example.munchy_crunchy.model.Categoria;
import com.example.munchy_crunchy.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAll(){
        return categoriaRepository.findAll();
    }

    public Categoria saveCategoria(Categoria categoria) throws IllegalArgumentException {
        if (categoria == null) {
            throw new IllegalArgumentException();
        }else{
            try {
                return categoriaRepository.save(categoria);
            } catch (DataAccessException e) {
                throw new IllegalArgumentException();
            }
        }
    }

    public void deleteCategoria(Integer id) throws IllegalArgumentException {
        try {
            categoriaRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException();
        }
    }

    public Optional<Categoria> findCategoriaById(Integer id) throws IllegalArgumentException {
        try {
            Optional<Categoria> categoria = categoriaRepository.findById(id);
            return categoria;
        } catch (DataAccessException e) {
            throw new IllegalArgumentException();
        }
    }
}
