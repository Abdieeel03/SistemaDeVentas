/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.utp.sistemadeventas.dao.interfaces;

/**
 *
 * @author marce
 * @param <T>
 */
public interface Persistible<T> {

    void crearArchivo();

    void guardarEnArchivo(T elemento);

    void eliminarDeArchivo(T elemento);

    void actualizarDeArchivo(T elemento);

    void leerArchivo();
    
    int obtenerUltimoIndice();
}
