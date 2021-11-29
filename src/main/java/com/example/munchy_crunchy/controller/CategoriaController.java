package com.example.munchy_crunchy.controller;

import com.example.munchy_crunchy.model.Categoria;
import com.example.munchy_crunchy.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/all")
    public ResponseEntity<List<Categoria>> getAllCategoria(){
        List<Categoria> categoria = categoriaService.getAll();
        if(categoria.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Categoria>>(categoria, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Categoria> createCategoria(Categoria categoria){
       try {
           Categoria ca = new Categoria();
           ca = categoriaService.saveCategoria(categoria);
           return new ResponseEntity<Categoria>(ca, HttpStatus.CREATED);
       }catch (IllegalArgumentException e) {
           return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
       }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Categoria> deleteCategoria(@PathVariable int id){
        try{
            categoriaService.deleteCategoria(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable int id, String descripcion, Categoria categoria){
        try{
            Categoria ca = new Categoria();
            Optional<Categoria> c = categoriaService.findCategoriaById(id);
            ca = c.get();
            Categoria resultado = categoriaService.saveCategoria(ca);
            return new ResponseEntity<Categoria>(resultado, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
