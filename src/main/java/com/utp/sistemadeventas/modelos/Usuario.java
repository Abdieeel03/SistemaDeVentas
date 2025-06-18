package com.utp.sistemadeventas.modelos;

public class Usuario {

    private String nombre, usuario, contraseña, descripcion;
    private int idRol, idUsuario;
    private Rol rol;

    public Usuario(String nombre, String usuario, String contraseña, String descripcion, int idRol, int idUsuario) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.descripcion = descripcion;
        this.idRol = idRol;
        this.idUsuario = idUsuario;
    }

    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
