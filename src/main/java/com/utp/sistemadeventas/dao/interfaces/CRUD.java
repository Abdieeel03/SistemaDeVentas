/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.utp.sistemadeventas.dao.interfaces;

import java.util.List;

/**
 *
 * @author marce
 * @param <T>
 */
public interface CRUD<T> {

    void agregar(T entidad);

    void eliminar(String id);

    void actualizar(T entidad);

    T buscarPorId(String id);

    List<T> listar();
}
