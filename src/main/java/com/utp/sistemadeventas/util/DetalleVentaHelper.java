package com.utp.sistemadeventas.util;

import com.utp.sistemadeventas.dao.DetalleVentaDAO;
import com.utp.sistemadeventas.dao.ProductoDAO;
import com.utp.sistemadeventas.modelos.DetalleVenta;
import com.utp.sistemadeventas.modelos.Producto;

/**
 *
 * @author Abdieeel
 */
public class DetalleVentaHelper {

    public static String obtenerNombreProducto(int idProducto, ProductoDAO productoDAO) {
        Producto p = productoDAO.buscarPorId(String.valueOf(idProducto));
        return p != null ? p.getNombre() : "Producto no encontrado";
    }

    public static double calcularSubtotal(int cantidad, double precioUnitario) {
        return cantidad * precioUnitario;
    }

    public static double obtenerPrecioUnitario(int idProducto, ProductoDAO productoDAO) {
        Producto p = productoDAO.buscarPorId(String.valueOf(idProducto));
        return p != null ? p.getPrecioVenta() : 0.0;
    }

    public static DetalleVenta crearDetalle(int idVenta, int idProducto, int cantidad, ProductoDAO productoDAO) {
        double precioUnitario = obtenerPrecioUnitario(idProducto, productoDAO);
        double subtotal = calcularSubtotal(cantidad, precioUnitario);
        return new DetalleVenta(0, idVenta, idProducto, cantidad, precioUnitario, subtotal);
    }

    public static void mostrarDetallesVenta(int idVenta, DetalleVentaDAO detalleDAO, ProductoDAO productoDAO) {
        System.out.println("Detalles de la Venta ID: " + idVenta);
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-5s %-25s %-10s %-15s %-10s\n", "ID", "Producto", "Cantidad", "Precio Unit.", "Subtotal");
        System.out.println("-------------------------------------------------------------");

        for (DetalleVenta d : detalleDAO.listar()) {
            if (d.getIdVenta() == idVenta) {
                String nombreProducto = obtenerNombreProducto(d.getIdProducto(), productoDAO);
                System.out.printf("%-5d %-25s %-10d %-15.2f %-10.2f\n",
                        d.getIdDetalle(),
                        nombreProducto,
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal());
            }
        }

        System.out.println("-------------------------------------------------------------");
    }

    public static void mostrarTodosLosDetalles(DetalleVentaDAO detalleDAO, ProductoDAO productoDAO) {
        System.out.println("LISTA DE TODOS LOS DETALLES DE VENTA");
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-5s %-8s %-25s %-10s %-15s %-10s\n",
                "ID", "Venta", "Producto", "Cantidad", "Precio Unit.", "Subtotal");
        System.out.println("--------------------------------------------------------------------------");

        for (DetalleVenta d : detalleDAO.listar()) {
            String nombreProducto = obtenerNombreProducto(d.getIdProducto(), productoDAO);
            System.out.printf("%-5d %-8d %-25s %-10d %-15.2f %-10.2f\n",
                    d.getIdDetalle(),
                    d.getIdVenta(),
                    nombreProducto,
                    d.getCantidad(),
                    d.getPrecioUnitario(),
                    d.getSubtotal()
            );
        }

        System.out.println("--------------------------------------------------------------------------");
    }
}
