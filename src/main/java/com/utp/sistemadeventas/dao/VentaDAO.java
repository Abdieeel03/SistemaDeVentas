package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.modelos.Venta;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Abdiel
 */
public final class VentaDAO implements CRUD<Venta>, Persistible<Venta> {

    private final List<Venta> lista;
    private final String FOLDER = "datos";
    private final String NAMEFILE = "ventas.csv";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%d,%s,%.2f,%d,%s\n";
    private final File archivo;
    private int indice;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public VentaDAO() {
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
    public void agregar(Venta entidad) {
        entidad.setId_venta(indice++);
        guardarEnArchivo(entidad);
        lista.add(entidad);
    }

    @Override
    public void eliminar(String id) {
        Venta v = buscarPorId(id);
        if (v != null) {
            lista.remove(v);
            eliminarDeArchivo(v);
        }
    }

    @Override
    public void actualizar(Venta entidad) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId_venta() == entidad.getId_venta()) {
                lista.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public Venta buscarPorId(String id) {
        try {
            int idVenta = Integer.parseInt(id);
            for (Venta v : lista) {
                if (v.getId_venta() == idVenta) {
                    return v;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("ID no válido: " + id);
        }
        return null;
    }

    @Override
    public List<Venta> listar() {
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
    public void guardarEnArchivo(Venta elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(Locale.US, FORMAT,
                    elemento.getId_venta(),
                    sdf.format(elemento.getFechaVenta()),
                    elemento.getTotal(),
                    elemento.getIdMedioPago(),
                    elemento.getIdCliente()
            );
        } catch (IOException e) {
            System.err.println("Error al guardar venta: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo(Venta elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Venta venta : lista) {
                writer.printf(Locale.US, FORMAT,
                        venta.getId_venta(),
                        sdf.format(venta.getFechaVenta()),
                        venta.getTotal(),
                        venta.getIdMedioPago(),
                        venta.getIdCliente()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar venta: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo(Venta elemento) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Venta venta : lista) {
                writer.printf(Locale.US, FORMAT,
                        venta.getId_venta(),
                        sdf.format(venta.getFechaVenta()),
                        venta.getTotal(),
                        venta.getIdMedioPago(),
                        venta.getIdCliente()
                );
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar venta: " + e.getMessage());
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
                    int id = Integer.parseInt(partes[0].trim());
                    Date fecha = sdf.parse(partes[1].trim());
                    double total = Double.parseDouble(partes[2].trim());
                    int idMedioPago = Integer.parseInt(partes[3].trim());
                    String idCliente = partes[4].trim();

                    lista.add(new Venta(id, fecha, total, idMedioPago, idCliente));
                }
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error: " + e.getMessage());
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
                    int id = Integer.parseInt(partes[0].trim());
                    if (id > ultimoIndice) {
                        ultimoIndice = id;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al obtener último ID: " + e.getMessage());
        }
        return ultimoIndice;
    }
}
