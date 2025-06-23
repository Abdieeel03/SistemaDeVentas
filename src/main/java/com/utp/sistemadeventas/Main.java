/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.vistas.*;
import com.utp.sistemadeventas.controlador.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        controlador.iniciarEjecucion();

        long inicio = System.currentTimeMillis();
        escribirConBuffer("archivo_buffer.txt");
        long fin = System.currentTimeMillis();
        System.out.println("Con buffer: " + (fin - inicio) + " ms");
        long resultado1 = (fin - inicio);

        inicio = System.currentTimeMillis();
        escribirSinBuffer("archivo_nobuffer.txt");
        fin = System.currentTimeMillis();
        System.out.println("Sin buffer: " + (fin - inicio) + " ms");
        long resultado2 = (fin - inicio);

        double porcentaje = ((double) (resultado2 - resultado1) / resultado2) * 100;
        System.out.printf("Mejora: %.2f%%\n", porcentaje);
    }

    public static void escribirSinBuffer(String ruta) {
        try (FileWriter fw = new FileWriter(ruta)) {
            for (int i = 0; i < 1_000_000; i++) {
                fw.write("Línea número " + i + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escribirConBuffer(String ruta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            for (int i = 0; i < 1_000_000; i++) {
                bw.write("Línea número " + i + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
