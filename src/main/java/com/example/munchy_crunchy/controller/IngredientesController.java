package com.example.munchy_crunchy.controller;

import com.example.munchy_crunchy.model.Ingredientes;
import com.example.munchy_crunchy.service.IngredientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredientes")
public class IngredientesController {
    @Autowired
    private IngredientesService ingredientesService;

    @GetMapping("/all")
    public ResponseEntity<List<Ingredientes>> getAllIngredientes(){
        List<Ingredientes> ingrediente= ingredientesService.getAll();
        if(ingrediente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ingredientes>>(ingrediente,HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Ingredientes> createIngrediente(Ingredientes ingrediente){
        try {
            Ingredientes us = new Ingredientes();
            us=ingredientesService.saveIngrediente(ingrediente);
            return new ResponseEntity<Ingredientes>(us,HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Ingredientes> deleteIngrediente(@PathVariable int id){
        try {
            ingredientesService.deleteIngrediente(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/update/{id}")
    public ResponseEntity<Ingredientes> updateIngrediente(@PathVariable int id, Ingredientes ingrediente){
        try {
            Ingredientes us = new Ingredientes();
            Optional<Ingredientes> u = ingredientesService.findIngredienteById(id);
            us=u.get();
            Ingredientes resultado = ingredientesService.saveIngrediente(us);
            return new ResponseEntity<Ingredientes>(resultado, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
