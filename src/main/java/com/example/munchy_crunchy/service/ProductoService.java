package com.example.munchy_crunchy.service;

import com.example.munchy_crunchy.model.Producto;
import com.example.munchy_crunchy.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAll(){
        return productoRepository.findAll();
    }

    public Producto saveProducto(Producto producto)throws IllegalArgumentException{
        if(producto == null) {
            throw new IllegalArgumentException();
        }else{
            try{
                return productoRepository.saveAndFlush(producto);
            }catch (DataAccessException e){
                throw new IllegalArgumentException();
            }
        }
    }


    public void deleteProducto(Integer id) throws NullPointerException{
        if(id.equals(null) || id != 0) {
            try {
                productoRepository.deleteById(id);
            } catch (DataAccessException e) {
                throw new NullPointerException();
            }
        }else {
            throw new NullPointerException();
        }
    }

    public Producto findProductoByNombre(String nombre) throws NullPointerException{
        if(nombre != null) {
            try {
                Producto producto = productoRepository.getProductoByNombre(nombre);
                return producto;
            } catch (DataAccessException e) {
                throw new NullPointerException();
            }
        }else {
            throw new NullPointerException();
        }
    }

    public Producto update(Producto producto) throws IllegalArgumentException{
        if(producto != null){
            Producto change =  productoRepository.getById(producto.getId());
            if(change !=null){
                change.setNombre(producto.getNombre());
                change.setCantidad(producto.getCantidad());
                change.setValor(producto.getValor());
                change.setUnidadMedida(producto.getUnidadMedida());
                return productoRepository.save(producto);
            }else {
                throw new IllegalArgumentException();
            }
        }else {
            throw new IllegalArgumentException();
        }
    }



}
