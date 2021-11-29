package com.example.munchy_crunchy.repository;


import com.example.munchy_crunchy.model.Producto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    Producto getProductoByNombre(String nombre);

}
