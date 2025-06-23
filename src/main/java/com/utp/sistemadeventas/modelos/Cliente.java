package com.utp.sistemadeventas.modelos;

public class Cliente {

    private String idCliente, nombre;

    public Cliente() {
    }

    public Cliente(String id_cliente, String nombre) {
        this.idCliente = id_cliente;
        this.nombre = nombre;
    }

    public String getId_cliente() {
        return idCliente;
    }

    public void setId_cliente(String id_cliente) {
        this.idCliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
