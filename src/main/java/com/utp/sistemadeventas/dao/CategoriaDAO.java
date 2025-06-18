package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
import com.utp.sistemadeventas.estructuras.Arreglo;
import com.utp.sistemadeventas.modelos.Categoria;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abdiel
 */
public final class CategoriaDAO implements CRUD<Categoria>, Persistible<Categoria> {

    private final Arreglo<Categoria> arregloCategoria;
    private final int CAPACIDAD_INICIAL = 7;
    private final String NAMEFILE = "categoria.csv";
    private final String FOLDER = "datos";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%s,%s\n";
    private int indice = 1;
    private final File archivo;

    public CategoriaDAO() {
        arregloCategoria = new Arreglo<>(CAPACIDAD_INICIAL);

        File carpeta = new File(FOLDER);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        archivo = new File(PATHFILE);
        crearArchivo(NAMEFILE);

        indice = obtenerUltimoIndice() + 1;
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
            eliminarDeArchivo(c);
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

    @Override
    public void crearArchivo(String recursoNombre) {
        if (!archivo.exists()) {
            try (InputStream in = getClass().getResourceAsStream("/recursos/" + recursoNombre); OutputStream out = new FileOutputStream(archivo)) {

                if (in == null) {
                    System.err.println("No se encontró el recurso: " + recursoNombre);
                    archivo.createNewFile();
                    return;
                }

                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesLeidos);
                }

            } catch (IOException e) {
                System.err.println("Error al copiar el recurso '" + recursoNombre + "': " + e.getMessage());
            }
        }
    }

    @Override
    public void guardarEnArchivo(Categoria elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(FORMAT, elemento.getIdCategoria(), elemento.getNombre());
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo(Categoria elemento) {
        List<Categoria> listaActual = listar();
        listaActual.removeIf(c -> c.getIdCategoria() == elemento.getIdCategoria());
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Categoria c : listaActual) {
                writer.printf(FORMAT, c.getIdCategoria(), c.getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar del archivo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo(Categoria elemento) {
        List<Categoria> listaActual = listar();
        for (int i = 0; i < listaActual.size(); i++) {
            if (listaActual.get(i).getIdCategoria() == elemento.getIdCategoria()) {
                listaActual.set(i, elemento);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Categoria c : listaActual) {
                writer.printf(FORMAT, c.getIdCategoria(), c.getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo: " + e.getMessage());
        }
    }

    @Override
    public void leerArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    int idCategoria = Integer.parseInt(partes[0].trim());
                    String nombreCategoria = partes[1].trim();
                    arregloCategoria.agregar(new Categoria(idCategoria, nombreCategoria));

                    if (idCategoria >= indice) {
                        indice = idCategoria + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
    }

    @Override
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
