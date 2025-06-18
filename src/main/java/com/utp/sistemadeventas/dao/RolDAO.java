package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.modelos.Rol;
import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
import com.utp.sistemadeventas.estructuras.Arreglo;
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
public final class RolDAO implements CRUD<Rol>, Persistible<Rol> {

    private final Arreglo<Rol> arregloRol;
    private final int CAPACIDAD_INICIAL = 3;
    private final String NAMEFILE = "roles.csv";
    private final String FOLDER = "datos";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%s,%s\n";
    private int indice;
    private final File archivo;

    public RolDAO() {
        arregloRol = new Arreglo<>(CAPACIDAD_INICIAL);

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
    public void agregar(Rol entidad) {
        entidad.setIdRol(indice);
        arregloRol.agregar(entidad);
        guardarEnArchivo(entidad);
        indice++;
    }

    @Override
    public void eliminar(String id) {
        Rol rol = buscarPorId(id);
        if (rol != null) {
            arregloRol.eliminar(rol);
            eliminarDeArchivo(rol);
        }
    }

    @Override
    public void actualizar(Rol entidad) {
        for (int i = 0; i < arregloRol.tamanio(); i++) {
            Rol actual = arregloRol.obtener(i);
            if (actual.getIdRol() == entidad.getIdRol()) {
                arregloRol.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public Rol buscarPorId(String id) {
        int idBuscado = Integer.parseInt(id);
        for (int i = 0; i < arregloRol.tamanio(); i++) {
            Rol rol = arregloRol.obtener(i);
            if (rol.getIdRol() == idBuscado) {
                return rol;
            }
        }
        return null;
    }

    @Override
    public List<Rol> listar() {
        List<Rol> lista = new ArrayList<>();
        for (int i = 0; i < arregloRol.tamanio(); i++) {
            lista.add(arregloRol.obtener(i));
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
    public void guardarEnArchivo(Rol elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(FORMAT, elemento.getIdRol(), elemento.getNombre());
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo(Rol elemento) {
        List<Rol> listaActual = listar();
        listaActual.removeIf(r -> r.getIdRol() == elemento.getIdRol());

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Rol r : listaActual) {
                writer.printf(FORMAT, r.getIdRol(), r.getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar del archivo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo(Rol elemento) {
        List<Rol> listaActual = listar();
        for (int i = 0; i < listaActual.size(); i++) {
            if (listaActual.get(i).getIdRol() == elemento.getIdRol()) {
                listaActual.set(i, elemento);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Rol r : listaActual) {
                writer.printf(FORMAT, r.getIdRol(), r.getNombre());
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
                    int idRol = Integer.parseInt(partes[0].trim());
                    String nombreRol = partes[1].trim();
                    arregloRol.agregar(new Rol(idRol, nombreRol));

                    if (idRol >= indice) {
                        indice = idRol + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
    }

    @Override
    public int obtenerUltimoIndice() {
        int ultimoIndice = 0;
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
