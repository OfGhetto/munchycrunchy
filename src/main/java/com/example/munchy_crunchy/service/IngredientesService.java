package com.example.munchy_crunchy.service;

import com.example.munchy_crunchy.model.Ingredientes;
import com.example.munchy_crunchy.repository.IngredientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class IngredientesService {
    @Autowired
    private IngredientesRepository ingredientesRepository;

    public List<Ingredientes> getAll() {
        return ingredientesRepository.findAll();
    }

    public Ingredientes saveIngrediente(Ingredientes ingrediente) throws IllegalArgumentException{
        if(ingrediente == null) {
            throw new IllegalArgumentException();
        }else {
            try {
                return ingredientesRepository.save(ingrediente);
            } catch (DataAccessException e) {
                throw new IllegalArgumentException();
            }
        }
    }

    public void deleteIngrediente(Integer id)  throws IllegalArgumentException {
        try {
            ingredientesRepository.deleteById(id);
        }catch (DataAccessException e) {
            throw new IllegalArgumentException();
        }

    }

    public Optional<Ingredientes> findIngredienteById(Integer id) throws IllegalArgumentException{
        try {
            Optional<Ingredientes> ingrediente = ingredientesRepository.findById(id);
            return ingrediente;
        }catch(DataAccessException e) {
            throw new IllegalArgumentException();
        }

    }
}
