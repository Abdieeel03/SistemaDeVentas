package com.utp.sistemadeventas.modelos;

public class Usuario {

    private String idUsuario;
    private String nombre;
    private String usuario;
    private String contraseña;
    private String descripcion;
    private int idRol;

    // Constructor
    public Usuario(String idUsuario, String nombre, String usuario, String contraseña, String descripcion, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.descripcion = descripcion;
        this.idRol = idRol;
    }

    public Usuario() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
