package com.utp.sistemadeventas.modelos;

import java.util.Date;

public class Venta {

    private int id_venta;
    private Date fechaVenta;
    private double total;
    private int idMedioPago;
    private String idCliente;

    public Venta() {

    }

    public Venta(int id_venta, Date fechaVenta, double total, int idMedioPago, String idCliente) {
        this.id_venta = id_venta;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.idMedioPago = idMedioPago;
        this.idCliente = idCliente;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(int idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

}
