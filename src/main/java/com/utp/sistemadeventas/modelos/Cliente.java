package com.utp.sistemadeventas.modelos;

public class Cliente {

    private String id_cliente, nombre;

    public Cliente() {
    }

    public Cliente(String id_cliente, String nombre) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
