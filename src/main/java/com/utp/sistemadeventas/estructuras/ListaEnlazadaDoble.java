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
public class ListaEnlazadaDoble<T> implements Modificable<T>, Accesible<T> {

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private Nodo<T> nodoActual; // 🔁 Puntero de navegación
    private int size;

    public ListaEnlazadaDoble() {
        cabeza = null;
        cola = null;
        nodoActual = null;
        size = 0;
    }

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (cabeza == null) {
            cabeza = cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            cola = nuevoNodo;
        }
        size++;
    }

    @Override
    public void eliminar(T elemento) {
        if (cabeza == null) {
            return;
        }
        // Caso especial: cabeza
        if (cabeza.dato.equals(elemento)) {
            if (nodoActual == cabeza) {
                nodoActual = cabeza.siguiente != null ? cabeza.siguiente : null;
            }
            cabeza = cabeza.siguiente;
            if (cabeza != null) {
                cabeza.anterior = null;
            } else {
                cola = null;
            }
            size--;
            return;
        }

        Nodo<T> nodoTemp = cabeza;
        while (nodoTemp != null && !nodoTemp.dato.equals(elemento)) {
            nodoTemp = nodoTemp.siguiente;
        }

        if (nodoTemp != null) {
            if (nodoActual == nodoTemp) {
                if (nodoTemp.siguiente != null) {
                    nodoActual = nodoTemp.siguiente;
                } else if (nodoTemp.anterior != null) {
                    nodoActual = nodoTemp.anterior;
                } else {
                    nodoActual = null;
                }
            }

            if (nodoTemp == cola) {
                cola = nodoTemp.anterior;
                if (cola != null) {
                    cola.siguiente = null;
                }
            } else {
                nodoTemp.anterior.siguiente = nodoTemp.siguiente;
                nodoTemp.siguiente.anterior = nodoTemp.anterior;
            }
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
        nodoActual = nodoTemp;
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

    public void set(int index, T nuevoElemento) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }

        Nodo<T> nodoTemp = cabeza;
        for (int i = 0; i < index; i++) {
            nodoTemp = nodoTemp.siguiente;
        }

        nodoTemp.dato = nuevoElemento;
    }

    // 🔁 Métodos para navegación secuencial
    public void irAlInicio() {
        nodoActual = cabeza;
    }

    public void irAlFinal() {
        nodoActual = cola;
    }

    public boolean anterior() {
        if (nodoActual != null && nodoActual.anterior != null) {
            nodoActual = nodoActual.anterior;
            return true;
        }
        return false;
    }

    public boolean siguiente() {
        if (nodoActual != null && nodoActual.siguiente != null) {
            nodoActual = nodoActual.siguiente;
            return true;
        }
        return false;
    }

    public T obtenerActual() {
        return nodoActual != null ? nodoActual.dato : null;
    }

    private static class Nodo<T> {

        T dato;
        Nodo<T> siguiente;
        Nodo<T> anterior;

        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
            this.anterior = null;
        }

    }

}
