package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.modelos.Proveedor;
import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
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
public final class ProveedorDAO implements CRUD<Proveedor>, Persistible<Proveedor> {

    private final List<Proveedor> lista;
    private final String FOLDER = "datos";
    private final String NAMEFILE = "proveedores.csv";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%s,%s,%s,%s,%s\n";
    private final File archivo;

    public ProveedorDAO() {
        lista = new ArrayList<>();
        File carpeta = new File(FOLDER);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        archivo = new File(PATHFILE);
        crearArchivo(NAMEFILE);
        leerArchivo();
    }

    @Override
    public void agregar(Proveedor entidad) {
        if (buscarPorId(entidad.getIdProveedor()) == null) {
            guardarEnArchivo(entidad);
            lista.add(entidad);
            return;
        }
        System.err.println("Ya existe un proveedor con ese ID");
    }

    @Override
    public void eliminar(String id) {
        Proveedor p = buscarPorId(id);
        if (p != null) {
            lista.remove(p);
            eliminarDeArchivo(p);
        }
    }

    @Override
    public void actualizar(Proveedor entidad) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdProveedor().equals(entidad.getIdProveedor())) {
                lista.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public Proveedor buscarPorId(String id) {
        for (Proveedor p : lista) {
            if (p.getIdProveedor().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Proveedor> listar() {
        return new ArrayList<>(lista);
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
    public void guardarEnArchivo(Proveedor elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(FORMAT, elemento.getIdProveedor(), elemento.getNombre(), elemento.getDireccion(), elemento.getTelefono(), elemento.getPaginaWeb());
        } catch (IOException e) {
            System.err.println("Error al guardar proveedor: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo(Proveedor elemento) {
        List<Proveedor> listaActual = new ArrayList<>(lista);
        listaActual.removeIf(p -> p.getIdProveedor().equals(elemento.getIdProveedor()));

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Proveedor p : listaActual) {
                writer.printf(FORMAT, p.getIdProveedor(), p.getNombre(), p.getDireccion(), p.getTelefono(), p.getPaginaWeb());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar proveedor del archivo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo(Proveedor elemento) {
        List<Proveedor> listaActual = new ArrayList<>(lista);
        for (int i = 0; i < listaActual.size(); i++) {
            if (listaActual.get(i).getIdProveedor().equals(elemento.getIdProveedor())) {
                listaActual.set(i, elemento);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Proveedor p : listaActual) {
                writer.printf(FORMAT, p.getIdProveedor(), p.getNombre(), p.getDireccion(), p.getTelefono(), p.getPaginaWeb());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo de proveedores: " + e.getMessage());
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
                if (partes.length >= 5) {
                    Proveedor p = new Proveedor();
                    p.setIdProveedor(partes[0].trim());
                    p.setNombre(partes[1].trim());
                    p.setDireccion(partes[2].trim());
                    p.setTelefono(partes[3].trim());
                    p.setPaginaWeb(partes[4].trim());
                    lista.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de proveedores: " + e.getMessage());
        }
    }

    @Override
    public int obtenerUltimoIndice() {
        // No aplica porque el ID es definido manualmente (RUC, etc.)
        return -1;
    }

}
