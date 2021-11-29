package com.example.munchy_crunchy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Medio_pago {

    @Id
    @GeneratedValue
    private Integer id;
    private String descripcion;
    private String forma_pago;

    public Medio_pago(Integer id, String descripcion, String forma_pago) {
        this.id = id;
        this.descripcion = descripcion;
        this.forma_pago = forma_pago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

}
