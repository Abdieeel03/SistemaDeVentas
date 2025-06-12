/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.sistemadeventas.estructuras;

import com.utp.sistemadeventas.estructuras.interfaces.Accesible;
import com.utp.sistemadeventas.estructuras.interfaces.Modificable;
import com.utp.sistemadeventas.estructuras.interfaces.PilaModificable;
import java.util.LinkedList;

/**
 *
 * @author marce
 * @param <T>
 */
public class Pila<T> implements PilaModificable<T>, Accesible<T> {

    private LinkedList<T> pila;

    public Pila() {
        pila = new LinkedList<>();
    }

    @Override
    public void apilar(T elemento) {
        pila.push(elemento);
    }

    @Override
    public T desapilar() {
        if (!estaVacia()) {
            return pila.pop();
        } else {
            System.out.println("La pila está vacía.");
            return null;
        }
    }

    @Override
    public T cima() {
        if (!estaVacia()) {
            return pila.peek();
        } else {
            System.out.println("La pila está vacía.");
            return null;
        }
    }

    @Override
    public T obtener(int index) {
        throw new UnsupportedOperationException("Acceso por índice no permitido en una pila.");
    }

    @Override
    public boolean estaVacia() {
        return pila.isEmpty();
    }

    @Override
    public int tamanio() {
        return pila.size();
    }

}
