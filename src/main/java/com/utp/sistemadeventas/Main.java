/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.dao.RolDAO;
import com.utp.sistemadeventas.modelos.Rol;
import com.utp.sistemadeventas.vistas.VtnLogin;
import java.util.List;


/**
 *
 * @author marce
 */
public class Main {

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        System.out.println("Hello World!");
        RolDAO rolDAO = new RolDAO();
        List<Rol> lista = rolDAO.listar();
        for(Rol r : lista){
            System.out.println(r.getIdRol()+" = "+r.getNombre());
        }
    }
}
