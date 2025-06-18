package com.utp.sistemadeventas.modelos;

public class Producto {

    private int idProducto;
    private int idCategoria;
    private String nombre;
    private String idProveedor;
    private double precioCompra;
    private double precioVenta;
    private int stock;

    public Producto() {
    }

    public Producto(int idProducto, int idCategoria, String nombre, String idProveedor, double precioCompra, double precioVenta, int stock) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.idProveedor = idProveedor;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
