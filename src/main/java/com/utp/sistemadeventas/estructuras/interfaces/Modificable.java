package com.utp.sistemadeventas.estructuras.interfaces;

/**
 *
 * @author marce
 * @param <T>
 */
public interface Modificable<T> {

    void agregar(T elemento);

    void eliminar(T elemento);
}
