/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.utp.sistemadeventas.estructuras.interfaces;

/**
 *
 * @author marce
 * @param <T>
 */
public interface Accesible<T> {

    T obtener(int index);

    boolean estaVacia();

    int tamanio();
}
