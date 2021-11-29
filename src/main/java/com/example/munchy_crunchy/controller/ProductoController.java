package com.example.munchy_crunchy.controller;


import com.example.munchy_crunchy.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.munchy_crunchy.service.ProductoService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("/all")
    public ResponseEntity<List<Producto>> getAllProducto(){
        List<Producto>productoList = productoService.getAll();
        if(productoList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Producto>>(productoList, HttpStatus.OK);
    }

    @GetMapping("/create")
    public ResponseEntity<Producto> createProducto(Producto producto){
        Producto requested = productoService.saveProducto(producto);
        return new ResponseEntity<Producto>(requested, HttpStatus.CREATED);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable Integer id){
        try{
            productoService.deleteProducto(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable String nombre, Model model){
        try{
            Producto producto =  productoService.findProductoByNombre(nombre);
            Producto resultado = productoService.saveProducto(producto);
            return new ResponseEntity<>(resultado,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



}
