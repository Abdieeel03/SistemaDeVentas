package com.utp.sistemadeventas.modelos;

public class Categoria {

    private int idCategoria;
    private String nombre;

    public Categoria() {
    }

    public Categoria(int id_categoria, String nombre) {
        this.idCategoria = id_categoria;
        this.nombre = nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
