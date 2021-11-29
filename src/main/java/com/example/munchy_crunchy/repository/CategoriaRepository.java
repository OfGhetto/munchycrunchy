package com.example.munchy_crunchy.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.munchy_crunchy.model.Categoria;

@Repository
@Profile("jpa")
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
