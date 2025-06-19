package com.utp.sistemadeventas.util;

import com.utp.sistemadeventas.dao.RolDAO;
import com.utp.sistemadeventas.dao.UsuarioDAO;
import com.utp.sistemadeventas.modelos.Rol;
import com.utp.sistemadeventas.modelos.Usuario;

/**
 *
 * @author Abdieeel
 */
public class UsuarioHelper {

    public static String obtenerNombreRol(Usuario usuario, RolDAO rolDAO) {
        Rol rol = rolDAO.buscarPorId(String.valueOf(usuario.getIdRol()));
        return rol != null ? rol.getNombre() : "Rol no encontrado";
    }

    public static void mostrarUsuarios(UsuarioDAO usuarioDAO, RolDAO rolDAO) {
        System.out.println("Lista de Usuarios:");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-15s\n", "ID", "Nombre", "Usuario", "Rol");
        System.out.println("---------------------------------------------------------------");

        for (Usuario u : usuarioDAO.listar()) {
            String rol = obtenerNombreRol(u, rolDAO);
            System.out.printf("%-10s %-20s %-15s %-15s\n",
                    u.getIdUsuario(), u.getNombre(), u.getUsuario(), rol);
        }

        System.out.println("---------------------------------------------------------------");
    }
}
