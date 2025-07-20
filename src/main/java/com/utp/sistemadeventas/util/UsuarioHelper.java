package com.utp.sistemadeventas.util;

import com.utp.sistemadeventas.dao.RolDAO;
import com.utp.sistemadeventas.dao.UsuarioDAO;
import com.utp.sistemadeventas.modelos.Rol;
import com.utp.sistemadeventas.modelos.Usuario;
import java.util.List;

/**
 *
 * @author Abdieeel
 */
public class UsuarioHelper {

    public static String obtenerNombreRol(Usuario usuario, RolDAO rolDAO) {
        Rol rol = rolDAO.buscarPorId(String.valueOf(usuario.getIdRol()));
        return rol != null ? rol.getNombre() : "Rol no encontrado";
    }
    
    public static int obtenerIdRol(String nombreRol, RolDAO rolDAO){
        List<Rol> lista = rolDAO.listar();
        for(Rol r : lista){
            if(r.getNombre().equals(nombreRol)){
                return r.getIdRol();
            }
        }
        return -1;
    }
    
    public static String obtenerContrasena(Usuario usuario){
        return usuario.getContraseña();
    }

}
