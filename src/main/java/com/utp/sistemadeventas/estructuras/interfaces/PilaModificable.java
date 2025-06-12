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
public interface PilaModificable<T> {

    void apilar(T elemento);

    T desapilar();

    T cima();
}
