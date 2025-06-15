package com.utp.sistemadeventas.modelos;

import java.util.List;

public class Categoria {

    private int id_categoria;
    private String nombre;

    public Categoria() {
    }

    public int getIdCategoria() {
        return id_categoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.id_categoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
