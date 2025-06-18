package com.utp.sistemadeventas.modelos;

public class Proveedor {

    private String idProveedor;
    private String nombre;
    private String direccion;
    private String telefono;
    private String paginaWeb;

    public Proveedor() {
    }

    public Proveedor(String idProveedor, String nombre, String direccion, String telefono, String paginaWeb) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "idProveedor=" + idProveedor + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", paginaWeb=" + paginaWeb + '}';
    }

}
