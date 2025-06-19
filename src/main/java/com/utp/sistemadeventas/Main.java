/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.dao.ClienteDAO;
import com.utp.sistemadeventas.dao.DetalleVentaDAO;
import com.utp.sistemadeventas.dao.MedioPagoDAO;
import com.utp.sistemadeventas.dao.ProductoDAO;
import com.utp.sistemadeventas.dao.VentaDAO;
import com.utp.sistemadeventas.modelos.DetalleVenta;
import com.utp.sistemadeventas.modelos.Venta;
import com.utp.sistemadeventas.util.DetalleVentaHelper;
import com.utp.sistemadeventas.util.VentaHelper;
import com.utp.sistemadeventas.vistas.VtnLogin;
import java.util.Date;

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
        
        ProductoDAO productoDAO = new ProductoDAO();
        VentaDAO ventaDAO = new VentaDAO();
        DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        MedioPagoDAO medioPagoDAO = new MedioPagoDAO();

        Venta nuevaVenta = new Venta(0, new Date(), 0.0, 2, "00000000");
        ventaDAO.agregar(nuevaVenta);

        int idVenta = nuevaVenta.getId_venta();
        System.out.println("Venta creada con ID: " + idVenta);

        DetalleVenta detalle1 = DetalleVentaHelper.crearDetalle(idVenta, 4, 5, productoDAO); // Producto 1, cantidad 2
        DetalleVenta detalle2 = DetalleVentaHelper.crearDetalle(idVenta, 10, 2, productoDAO); // Producto 2, cantidad 1

        detalleDAO.agregar(detalle1);
        detalleDAO.agregar(detalle2);

        double totalVenta = detalle1.getSubtotal() + detalle2.getSubtotal();
        nuevaVenta.setTotal(totalVenta);
        ventaDAO.actualizar(nuevaVenta);

        VentaHelper.mostrarVentas(ventaDAO, clienteDAO, medioPagoDAO);
        DetalleVentaHelper.mostrarDetallesVenta(idVenta, detalleDAO, productoDAO);
        DetalleVentaHelper.mostrarTodosLosDetalles(detalleDAO, productoDAO);
    }
}
