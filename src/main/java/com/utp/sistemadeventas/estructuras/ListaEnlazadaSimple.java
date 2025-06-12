/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.sistemadeventas.estructuras;

import com.utp.sistemadeventas.estructuras.interfaces.Accesible;
import com.utp.sistemadeventas.estructuras.interfaces.Modificable;

/**
 *
 * @author marce
 */
public class ListaEnlazadaSimple<T> implements Modificable<T>, Accesible<T> {

    private Nodo<T> cabeza;
    private int size;

    public ListaEnlazadaSimple() {
        cabeza = null;
        size = 0;
    }

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> nodoTemp = cabeza;
            while (nodoTemp.siguiente != null) {
                nodoTemp = nodoTemp.siguiente;
            }
            nodoTemp.siguiente = nuevoNodo;
        }
        size++;
    }

    @Override
    public void eliminar(T elemento) {
        if (cabeza == null) {
            return;
        }

        if (cabeza.dato.equals(elemento)) {
            cabeza = cabeza.siguiente;
            size--;
            return;
        }

        Nodo<T> nodoTemp = cabeza;
        while (nodoTemp.siguiente != null && !nodoTemp.siguiente.dato.equals(elemento)) {
            nodoTemp = nodoTemp.siguiente;
        }

        if (nodoTemp.siguiente != null) {
            nodoTemp.siguiente = nodoTemp.siguiente.siguiente;
            size--;
        }
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }

        Nodo<T> nodoTemp = cabeza;
        for (int i = 0; i < index; i++) {
            nodoTemp = nodoTemp.siguiente;
        }
        return nodoTemp.dato;
    }

    @Override
    public boolean estaVacia() {
        return cabeza == null;
    }

    @Override
    public int tamanio() {
        return size;
    }

    private static class Nodo<T> {

        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

}
