package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.modelos.MedioPago;
import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
import com.utp.sistemadeventas.estructuras.Arreglo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdiel
 */
public final class MedioPagoDAO implements CRUD<MedioPago>, Persistible<MedioPago> {

    private Arreglo<MedioPago> arregloMedioPago;
    private final int CAPACIDAD_INICIAL = 2;
    private final String NAMEFILE = "medioPago.csv";
    private final String FORMAT = "%s,%s\n";
    public int indice;
    private File archivo;

    public MedioPagoDAO() {
        arregloMedioPago = new Arreglo<>(CAPACIDAD_INICIAL);
        archivo = new File(NAMEFILE);
        if (!archivo.exists()) {
            crearArchivo();
        }
        indice = obtenerUltimoIndice();
        leerArchivo();
    }

    @Override
    public void agregar(MedioPago entidad) {
        entidad.setIdMedioPago(indice);
        arregloMedioPago.agregar(entidad);
        guardarEnArchivo(entidad);
        indice++;
    }

    @Override
    public void eliminar(String id) {
        MedioPago mp = buscarPorId(id);
        if (mp != null) {
            arregloMedioPago.eliminar(mp);
            eliminarDeArchivo(mp);
        }
    }

    @Override
    public void actualizar(MedioPago entidad) {
        for (int i = 0; i < arregloMedioPago.tamanio(); i++) {
            MedioPago actual = arregloMedioPago.obtener(i);
            if (actual.getIdMedioPago() == entidad.getIdMedioPago()) {
                arregloMedioPago.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public MedioPago buscarPorId(String id) {
        int idBuscado = Integer.parseInt(id);
        for (int i = 0; i < arregloMedioPago.tamanio(); i++) {
            MedioPago mp = arregloMedioPago.obtener(i);
            if (mp.getIdMedioPago() == idBuscado) {
                return mp;
            }
        }
        return null;
    }

    @Override
    public List<MedioPago> listar() {
        List<MedioPago> lista = new ArrayList<>();
        for (int i = 0; i < arregloMedioPago.tamanio(); i++) {
            lista.add(arregloMedioPago.obtener(i));
        }
        return lista;
    }

    public void crearArchivo() {
        try {
            archivo.createNewFile();
        } catch (Exception e) {
            System.err.println("Error al crear el archivo de medioPago: " + e.getMessage());
        }
    }

    public void guardarEnArchivo(MedioPago elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(FORMAT, elemento.getIdMedioPago(), elemento.getNombre());
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    public void eliminarDeArchivo(MedioPago elemento) {
        List<MedioPago> listaActual = listar();
        listaActual.removeIf(r -> r.getIdMedioPago() == elemento.getIdMedioPago());

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (MedioPago mp : listaActual) {
                writer.printf(FORMAT, mp.getIdMedioPago(), mp.getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar del archivo: " + e.getMessage());
        }
    }

    public void actualizarDeArchivo(MedioPago elemento) {
        List<MedioPago> listaActual = listar();
        for (int i = 0; i < listaActual.size(); i++) {
            if (listaActual.get(i).getIdMedioPago() == elemento.getIdMedioPago()) {
                listaActual.set(i, elemento);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (MedioPago mp : listaActual) {
                writer.printf(FORMAT, mp.getIdMedioPago(), mp.getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

    public void leerArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    int idMedioPago = Integer.parseInt(partes[0].trim());
                    String nombreMedioPago = partes[1].trim();
                    arregloMedioPago.agregar(new MedioPago(idMedioPago, nombreMedioPago));

                    if (idMedioPago >= indice) {
                        indice = idMedioPago + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
    }

    public int obtenerUltimoIndice() {
        int ultimoIndice = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length >= 1) {
                    int idActual = Integer.parseInt(partes[0].trim());
                    if (idActual > ultimoIndice) {
                        ultimoIndice = idActual;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo para obtener último índice: " + e.getMessage());
        }
        return ultimoIndice;
    }

}
