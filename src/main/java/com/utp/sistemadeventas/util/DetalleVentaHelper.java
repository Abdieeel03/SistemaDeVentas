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

}
