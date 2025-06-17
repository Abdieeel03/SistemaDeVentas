package com.utp.sistemadeventas.modelos;

public class MedioPago {

    private int idMedioPago;
    private String nombre;

    public MedioPago() {
    }

    public MedioPago(int idMedioPago, String nombre) {
        this.idMedioPago = idMedioPago;
        this.nombre = nombre;
    }

    public int getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(int idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
