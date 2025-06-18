/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.dao.CategoriaDAO;
import com.utp.sistemadeventas.dao.RolDAO;
import com.utp.sistemadeventas.modelos.Categoria;
import com.utp.sistemadeventas.modelos.Rol;
import com.utp.sistemadeventas.vistas.VtnLogin;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author marce
 */
public class Main {

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        System.out.println("Hello World!");
        VtnLogin vtnLogin = new VtnLogin();
        vtnLogin.setVisible(true);
        JOptionPane.showMessageDialog(vtnLogin, "Hola");
        CategoriaDAO rDAO = new CategoriaDAO();
        List<Categoria> lista = rDAO.listar();
        for (Categoria r : lista) {
            JOptionPane.showMessageDialog(vtnLogin, r.getNombre());
        }
    }
}
