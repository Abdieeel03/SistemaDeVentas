/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.sistemadeventas.estructuras;

import com.utp.sistemadeventas.estructuras.interfaces.Accesible;
import com.utp.sistemadeventas.estructuras.interfaces.Modificable;
import java.util.ArrayList;

/**
 *
 * @author marce
 * @param <T>
 */
public class MiArrayList<T> implements Modificable<T>, Accesible<T> {

    private ArrayList<T> lista;

    public MiArrayList() {
        lista = new ArrayList<>();  // Inicializa el ArrayList interno
    }

    @Override
    public void agregar(T elemento) {
        lista.add(elemento);
    }

    @Override
    public void eliminar(T elemento) {
        if (!lista.remove(elemento)) {
            System.out.println("Elemento no encontrado.");
        }
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= tamanio()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }
        return lista.get(index);
    }

    @Override
    public boolean estaVacia() {
        return lista.isEmpty();
    }

    @Override
    public int tamanio() {
        return lista.size();
    }

}
