package com.utp.sistemadeventas.dao;

import com.utp.sistemadeventas.modelos.Usuario;
import com.utp.sistemadeventas.dao.interfaces.CRUD;
import com.utp.sistemadeventas.dao.interfaces.Persistible;
import com.utp.sistemadeventas.modelos.Rol;
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
public final class UsuarioDAO implements CRUD<Usuario>, Persistible<Usuario> {

    private final List<Usuario> lista;
    private final String FOLDER = "datos";
    private final String NAMEFILE = "usuarios.csv";
    private final String PATHFILE = FOLDER + File.separator + NAMEFILE;
    private final String FORMAT = "%s,%s,%s,%s,%s,%d\n";
    private final File archivo;

    public UsuarioDAO() {
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
    public void agregar(Usuario entidad) {
        if (buscarPorId(String.valueOf(entidad.getIdUsuario())) == null) {
            guardarEnArchivo(entidad);
            lista.add(entidad);
        } else {
            System.err.println("Ya existe un usuario con ese ID");
        }
    }

    @Override
    public void eliminar(String id) {
        Usuario u = buscarPorId(id);
        if (u != null) {
            lista.remove(u);
            eliminarDeArchivo(u);
        }
    }

    @Override
    public void actualizar(Usuario entidad) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdUsuario().equals(entidad.getIdUsuario())) {
                lista.set(i, entidad);
                actualizarDeArchivo(entidad);
                break;
            }
        }
    }

    @Override
    public Usuario buscarPorId(String id) {
        for (Usuario u : lista) {
            if (u.getIdUsuario().equals(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
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
    public void guardarEnArchivo(Usuario u) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            writer.printf(FORMAT, u.getIdUsuario(), u.getNombre(), u.getUsuario(), u.getContraseña(), u.getDescripcion(), u.getIdRol());
        } catch (IOException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDeArchivo(Usuario u) {
        List<Usuario> listaActual = new ArrayList<>(lista);
        listaActual.removeIf(us -> us.getIdUsuario().equals(u.getIdUsuario()));

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Usuario us : listaActual) {
                writer.printf(FORMAT, us.getIdUsuario(), us.getNombre(), us.getUsuario(), us.getContraseña(), us.getDescripcion(), us.getIdRol());
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar usuario del archivo: " + e.getMessage());
        }
    }

    @Override
    public void actualizarDeArchivo(Usuario u) {
        List<Usuario> listaActual = new ArrayList<>(lista);
        for (int i = 0; i < listaActual.size(); i++) {
            if (listaActual.get(i).getIdUsuario().equals(u.getIdUsuario())) {
                listaActual.set(i, u);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo)))) {
            for (Usuario us : listaActual) {
                writer.printf(FORMAT, us.getIdUsuario(), us.getNombre(), us.getUsuario(), us.getContraseña(), us.getDescripcion(), us.getIdRol());
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar archivo de usuarios: " + e.getMessage());
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
                    String idUsuario = partes[0].trim();
                    String nombre = partes[1].trim();
                    String usuario = partes[2].trim();
                    String contraseña = partes[3].trim();
                    String descripcion = partes[4].trim();
                    int idRol = Integer.parseInt(partes[5].trim());

                    lista.add(new Usuario(idUsuario, nombre, usuario, contraseña, descripcion, idRol));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de usuarios: " + e.getMessage());
        }
    }

    @Override
    public int obtenerUltimoIndice() {
        return -1; // No aplica porque el ID es definido externamente
    }

    public String obtenerNombreRol(Usuario usuario, RolDAO rolDAO) {
        Rol rol = rolDAO.buscarPorId(String.valueOf(usuario.getIdRol()));
        return rol != null ? rol.getNombre() : "Rol no encontrado";
    }

}
