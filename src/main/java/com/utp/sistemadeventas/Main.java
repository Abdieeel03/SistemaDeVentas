/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.vistas.*;
import com.utp.sistemadeventas.controlador.*;

/**
 *
 * @author marce
 */
public class Main {

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        /*Ventanas*/
        VtnInicio vtnInicio = new VtnInicio();
        VtnLogin vtnLogin = new VtnLogin();

        /*Inicializacion DAOs*/
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
        MedioPagoDAO medioPagoDAO = new MedioPagoDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        ProveedorDAO proveedorDAO = new ProveedorDAO();
        RolDAO rolDAO = new RolDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        VentaDAO ventaDAO = new VentaDAO();
        
        Controlador controlador = new Controlador(vtnInicio, vtnLogin, categoriaDAO, clienteDAO, detalleDAO, medioPagoDAO, productoDAO, proveedorDAO, rolDAO, usuarioDAO, ventaDAO);
        vtnLogin.setVisible(true);
    }
}
