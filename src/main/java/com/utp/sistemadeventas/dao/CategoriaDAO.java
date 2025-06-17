package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
import com.utp.sistemadeventas.estructuras.Arreglo;
import com.utp.sistemadeventas.modelos.Categoria;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdiel
 */
public final class CategoriaDAO implements CRUD<Categoria>, Persistible<Categoria> {

    private Arreglo<Categoria> arregloCategoria;
    private final int CAPACIDAD_INICIAL = 16;
    private final String NAMEFILE = "categoria.csv";
    private final String FORMAT = "%s,%s\n";
    private int indice = 1;
    private File archivo;

    public CategoriaDAO() {
        arregloCategoria = new Arreglo<>(CAPACIDAD_INICIAL);
        archivo = new File(NAMEFILE);
        if (!archivo.exists()) {
            crearArchivo();
        }
        leerArchivo();
    }

    @Override
    public void agregar(Categoria entidad) {
        entidad.setIdCategoria(indice);
        guardarEnArchivo(entidad);
        arregloCategoria.agregar(entidad);
        indice++;
    }

    @Override
    public void eliminar(String id) {
        Categoria c = buscarPorId(id);
        if (c != null) {
            arregloCategoria.eliminar(c);
        }
    }

    @Override
    public void actualizar(Categoria entidad) {
        for (int i = 0; i < arregloCategoria.tamanio(); i++) {
            Categoria actual = arregloCategoria.obtener(i);
            if (actual.getIdCategoria() == entidad.getIdCategoria()) {
                arregloCategoria.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public Categoria buscarPorId(String id) {
        int idBuscado = Integer.parseInt(id);
        for (int i = 0; i < arregloCategoria.tamanio(); i++) {
            Categoria c = arregloCategoria.obtener(i);
            if (c.getIdCategoria() == idBuscado) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        for (int i = 0; i < arregloCategoria.tamanio(); i++) {
            lista.add(arregloCategoria.obtener(i));
        }
        return lista;
    }

    public void crearArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void guardarEnArchivo(Categoria elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void eliminarDeArchivo(Categoria elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void actualizarDeArchivo(Categoria elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void leerArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int obtenerUltimoIndice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
