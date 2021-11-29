package com.example.munchy_crunchy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Producto {

    @Id
    @GeneratedValue
    private Integer id;
    private String nombre;
    private String unidadMedida;
    private Integer valor;
    private Integer cantidad;

    public Producto(Integer id, String nombre, String unidadMedida, Integer valor, Integer cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.valor = valor;
        this.cantidad = cantidad;
    }

    public Producto(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
