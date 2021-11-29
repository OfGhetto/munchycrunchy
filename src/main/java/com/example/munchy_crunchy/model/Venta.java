package com.example.munchy_crunchy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Venta {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer n_documento;
    private String fecha_resolucion;

    public Venta(Integer id, Integer n_documento, String fecha_resolucion) {
        this.id = id;
        this.n_documento = n_documento;
        this.fecha_resolucion = fecha_resolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getN_documento() {
        return n_documento;
    }

    public void setN_documento(Integer n_documento) {
        this.n_documento = n_documento;
    }

    public String getFecha_resolucion() {
        return fecha_resolucion;
    }

    public void setFecha_resolucion(String fecha_resolucion) {
        this.fecha_resolucion = fecha_resolucion;
    }

}
