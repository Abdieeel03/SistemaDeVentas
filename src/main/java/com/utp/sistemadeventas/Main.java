/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.dao.CategoriaDAO;
import com.utp.sistemadeventas.dao.ProductoDAO;
import com.utp.sistemadeventas.dao.ProveedorDAO;
import com.utp.sistemadeventas.dao.RolDAO;
import com.utp.sistemadeventas.dao.UsuarioDAO;
import com.utp.sistemadeventas.modelos.Categoria;
import com.utp.sistemadeventas.modelos.Producto;
import com.utp.sistemadeventas.modelos.Rol;
import com.utp.sistemadeventas.modelos.Usuario;
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
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        RolDAO rolDAO = new RolDAO();

        System.out.println("Lista de Usuarios:");
        System.out.println("------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-15s\n", "ID", "Nombre", "Usuario", "Rol");
        System.out.println("------------------------------------------------------");

        for (Usuario u : usuarioDAO.listar()) {
            String rol = usuarioDAO.obtenerNombreRol(u, rolDAO);
            System.out.printf("%-10s %-20s %-15s %-15s\n",
                    u.getIdUsuario(), u.getNombre(), u.getUsuario(), rol);
        }

        System.out.println("------------------------------------------------------");

        ProductoDAO productoDAO = new ProductoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProveedorDAO proveedorDAO = new ProveedorDAO();

        System.out.println("Lista de Productos:");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-30s %-20s %-25s %-10s %-10s %-6s\n",
                "ID", "Nombre", "Categoría", "Proveedor", "P. Compra", "P. Venta", "Stock");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        for (Producto p : productoDAO.listar()) {
            String nombreCategoria = productoDAO.obtenerNombreCategoria(p, categoriaDAO);
            String nombreProveedor = productoDAO.obtenerNombreProveedor(p, proveedorDAO);

            System.out.printf("%-5d %-30s %-20s %-25s %-10.2f %-10.2f %-6d\n",
                    p.getIdProducto(), p.getNombre(), nombreCategoria, nombreProveedor,
                    p.getPrecioCompra(), p.getPrecioVenta(), p.getStock());
        }

        System.out.println("---------------------------------------------------------------------------------------------------------");
    }
}
