package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
import com.utp.sistemadeventas.modelos.Categoria;
import com.utp.sistemadeventas.modelos.Producto;
import com.utp.sistemadeventas.modelos.Proveedor;
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
public final class ProductoDAO implements CRUD<Producto>, Persistible<Producto> {

    private final List<Producto> lista;
    private final String FOLDER = "datos";
    private final String NAMEFILE = "productos.csv";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%d,%d,%s,%s,%.2f,%.2f,%d\n";
    private final File archivo;
    private int indice;

    public ProductoDAO() {
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
    public void agregar(Producto entidad) {
        entidad.setIdProducto(indice++);
        lista.add(entidad);
        guardarEnArchivo(entidad);
    }

    @Override
    public void eliminar(String id) {
        Producto p = buscarPorId(id);
        if (p != null) {
            lista.remove(p);
            eliminarDeArchivo(p);
        }
    }

    @Override
    public void actualizar(Producto entidad) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdProducto() == entidad.getIdProducto()) {
                lista.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public Producto buscarPorId(String id) {
        try {
            int idBuscado = Integer.parseInt(id);
            for (Producto p : lista) {
                if (p.getIdProducto() == idBuscado) {
                    return p;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ID invalido: " + id);
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        return new ArrayList<>(lista);
    }

    @Override
    public void crearArchivo(String recursoNombre) {
        if (!archivo.exists()) {
            try (InputStream in = getClass().getResourceAsStream("/recursos/" + recursoNombre); OutputStream out = new FileOutputStream(archivo)) {

                if (in == null) {
                    archivo.createNewFile();
                    return;
                }

                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesLeidos);
                }

            } catch (IOException e) {
                System.err.println("Error al crear archivo: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardarEnArchivo(Producto p) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(FORMAT, p.getIdProducto(), p.getIdCategoria(), p.getNombre(), p.getIdProveedor(),
                    p.getPrecioCompra(), p.getPrecioVenta(), p.getStock());
        } catch (IOException e) {
            System.err.println("Error al guardar producto en archivo: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo(Producto p) {
        List<Producto> listaActual = listar();
        listaActual.removeIf(prod -> prod.getIdProducto() == p.getIdProducto());

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Producto prod : listaActual) {
                writer.printf(FORMAT, prod.getIdProducto(), prod.getIdCategoria(), prod.getNombre(),
                        prod.getIdProveedor(), prod.getPrecioCompra(), prod.getPrecioVenta(), prod.getStock());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar producto del archivo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo(Producto p) {
        List<Producto> listaActual = listar();
        for (int i = 0; i < listaActual.size(); i++) {
            if (listaActual.get(i).getIdProducto() == p.getIdProducto()) {
                listaActual.set(i, p);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Producto prod : listaActual) {
                writer.printf(FORMAT, prod.getIdProducto(), prod.getIdCategoria(), prod.getNombre(),
                        prod.getIdProveedor(), prod.getPrecioCompra(), prod.getPrecioVenta(), prod.getStock());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo de productos: " + e.getMessage());
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
                if (partes.length >= 7) {
                    int idProducto = Integer.parseInt(partes[0].trim());
                    int idCategoria = Integer.parseInt(partes[1].trim());
                    String nombre = partes[2].trim();
                    String idProveedor = partes[3].trim();
                    double precioCompra = Double.parseDouble(partes[4].trim());
                    double precioVenta = Double.parseDouble(partes[5].trim());
                    int stock = Integer.parseInt(partes[6].trim());

                    lista.add(new Producto(idProducto, idCategoria, nombre, idProveedor, precioCompra, precioVenta, stock));

                    if (idProducto >= indice) {
                        indice = idProducto + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de productos: " + e.getMessage());
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
