/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.sistemadeventas.estructuras;

import com.utp.sistemadeventas.estructuras.interfaces.Accesible;
import com.utp.sistemadeventas.estructuras.interfaces.ArregloDinamico;
import com.utp.sistemadeventas.estructuras.interfaces.Modificable;
import java.util.Arrays;

/**
 *
 * @author marce
 * @param <T>
 */
public class Arreglo<T> implements Modificable<T>, Accesible<T>, ArregloDinamico<T> {

    private Object[] arreglo;
    private int cantidadElementos;
    private int capacidad;

    public Arreglo(int capacidad) {
        this.capacidad = capacidad;
        this.arreglo = new Object[capacidad];
        this.cantidadElementos = 0;
    }

    @Override
    public void agregar(T elemento) {
        if (estaLleno()) {
            ampliarArreglo();
        }
        arreglo[cantidadElementos++] = elemento;
    }

    @Override
    public void eliminar(T elemento) {
        for (int i = 0; i < cantidadElementos; i++) {
            if (arreglo[i].equals(elemento)) {
                for (int j = i; j < cantidadElementos - 1; j++) {
                    arreglo[j] = arreglo[j + 1];
                }
                arreglo[--cantidadElementos] = null;
                return;
            }
        }
    }

    @Override
    public T obtener(int index) {
        if (index >= 0 && index < cantidadElementos) {
            return (T) arreglo[index];
        }
        throw new IndexOutOfBoundsException("Índice fuera de rango.");
    }

    @Override
    public boolean estaVacia() {
        return cantidadElementos == 0;
    }

    @Override
    public int tamanio() {
        return cantidadElementos;
    }

    @Override
    public void ampliarArreglo() {
        int nuevaCapacidad = capacidad + 10;
        capacidad = nuevaCapacidad;
        Object[] nuevoArreglo = Arrays.copyOf(arreglo, capacidad);
        arreglo = nuevoArreglo;
    }

    @Override
    public boolean estaLleno() {
        return cantidadElementos == capacidad;
    }

    @Override
    public void set(int index, T elemento) {
        if (index >= 0 && index < cantidadElementos) {
            arreglo[index] = elemento;
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }
    }

    public void listar() {
        for (int i = 0; i < cantidadElementos; i++) {
            System.out.println(arreglo[i]);
        }
    }

    public Object[] getArreglo() {
        return arreglo;
    }

    public void setArreglo(Object[] arreglo) {
        this.arreglo = arreglo;
    }

    public int getCantidadElementos() {
        return cantidadElementos;
    }

    public void setCantidadElementos(int cantidadElementos) {
        this.cantidadElementos = cantidadElementos;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}
