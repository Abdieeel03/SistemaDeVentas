package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.modelos.Cliente;
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
public final class ClienteDAO implements CRUD<Cliente>, Persistible<Cliente> {

    private final List<Cliente> lista;
    private final String NAMEFILE = "clientes.csv";
    private final String FOLDER = "datos";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%s,%s\n";
    private final File archivo;

    public ClienteDAO() {
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
    public void agregar(Cliente entidad) {
        if (buscarPorId(entidad.getId_cliente()) == null) {
            guardarEnArchivo(entidad);
            lista.add(entidad);
            return;
        }
        System.err.println("Ya existe un cliente con ese ID");
    }

    @Override
    public void eliminar(String id) {
        Cliente c = buscarPorId(id);
        if (c != null) {
            lista.remove(c);
            eliminarDeArchivo();
        }
    }

    @Override
    public void actualizar(Cliente entidad) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId_cliente().equals(entidad.getId_cliente())) {
                lista.set(i, entidad);
                actualizarDeArchivo();
                break;
            }
        }
    }

    @Override
    public Cliente buscarPorId(String id) {
        for (Cliente c : lista) {
            if (c.getId_cliente().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> listar() {
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
    public void guardarEnArchivo(Cliente elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(FORMAT, elemento.getId_cliente(), elemento.getNombre());
        } catch (IOException e) {
            System.err.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Cliente c : lista) {
                writer.printf(FORMAT, c.getId_cliente(), c.getNombre());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar del archivo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Cliente c : lista) {
                writer.printf(FORMAT, c.getId_cliente(), c.getNombre());
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
                    String id = partes[0].trim();
                    String nombre = partes[1].trim();
                    lista.add(new Cliente(id, nombre));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    @Override
    public int obtenerUltimoIndice() {
        return -1;
    }

}
