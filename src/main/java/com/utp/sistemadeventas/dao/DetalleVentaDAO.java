package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.modelos.DetalleVenta;
import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Abdiel
 */
public final class DetalleVentaDAO implements CRUD<DetalleVenta>, Persistible<DetalleVenta> {

    private final List<DetalleVenta> lista;
    private final String FOLDER = "datos";
    private final String NAMEFILE = "detalles.csv";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%d,%d,%d,%d,%.2f,%.2f\n";
    private final File archivo;
    private int indice;

    public DetalleVentaDAO() {
        lista = new ArrayList<>();
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
    public void agregar(DetalleVenta entidad) {
        entidad.setIdDetalle(indice++);
        guardarEnArchivo(entidad);
        lista.add(entidad);
    }

    @Override
    public void eliminar(String id) {
        DetalleVenta detalle = buscarPorId(id);
        if (detalle != null) {
            lista.remove(detalle);
            eliminarDeArchivo(detalle);
        }
    }

    @Override
    public void actualizar(DetalleVenta entidad) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdDetalle() == entidad.getIdDetalle()) {
                lista.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public DetalleVenta buscarPorId(String id) {
        try {
            int idDetalle = Integer.parseInt(id);
            for (DetalleVenta d : lista) {
                if (d.getIdDetalle() == idDetalle) {
                    return d;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("ID no válido: " + id);
        }
        return null;
    }

    @Override
    public List<DetalleVenta> listar() {
        return new ArrayList<>(lista);
    }

    @Override
    public void crearArchivo(String recursoNombre) {
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardarEnArchivo(DetalleVenta elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(Locale.US, FORMAT,
                    elemento.getIdDetalle(),
                    elemento.getIdVenta(),
                    elemento.getIdProducto(),
                    elemento.getCantidad(),
                    elemento.getPrecioUnitario(),
                    elemento.getSubtotal()
            );
        } catch (IOException e) {
            System.err.println("Error al guardar detalle: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo(DetalleVenta elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (DetalleVenta d : lista) {
                writer.printf(Locale.US, FORMAT,
                        d.getIdDetalle(),
                        d.getIdVenta(),
                        d.getIdProducto(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar detalle: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo(DetalleVenta elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (DetalleVenta d : lista) {
                writer.printf(Locale.US, FORMAT,
                        d.getIdDetalle(),
                        d.getIdVenta(),
                        d.getIdProducto(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo de detalle: " + e.getMessage());
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
                if (partes.length >= 6) {
                    int idDetalle = Integer.parseInt(partes[0].trim());
                    int idVenta = Integer.parseInt(partes[1].trim());
                    int idProducto = Integer.parseInt(partes[2].trim());
                    int cantidad = Integer.parseInt(partes[3].trim());
                    double precioUnitario = Double.parseDouble(partes[4].trim());
                    double subtotal = Double.parseDouble(partes[5].trim());
                    lista.add(new DetalleVenta(idDetalle, idVenta, idProducto, cantidad, precioUnitario, subtotal));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de detalle: " + e.getMessage());
        }
    }

    @Override
    public int obtenerUltimoIndice() {
        int ultimo = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length >= 1) {
                    int id = Integer.parseInt(partes[0].trim());
                    if (id > ultimo) {
                        ultimo = id;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al obtener último ID de detalle: " + e.getMessage());
        }
        return ultimo;
    }

}
