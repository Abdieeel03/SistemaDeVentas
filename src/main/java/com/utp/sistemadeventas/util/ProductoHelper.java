package com.utp.sistemadeventas.util;

import com.utp.sistemadeventas.dao.CategoriaDAO;
import com.utp.sistemadeventas.dao.ProductoDAO;
import com.utp.sistemadeventas.dao.ProveedorDAO;
import com.utp.sistemadeventas.modelos.Categoria;
import com.utp.sistemadeventas.modelos.Proveedor;
import com.utp.sistemadeventas.modelos.Producto;
import java.util.List;

/**
 *
 * @author marce
 */
public class ProductoHelper {

    public static String obtenerNombreCategoria(Producto producto, CategoriaDAO categoriaDAO) {
        Categoria categoria = categoriaDAO.buscarPorId(String.valueOf(producto.getIdCategoria()));
        return categoria != null ? categoria.getNombre() : "Categoría no encontrada";
    }

    public static int obtenerIdCategoria(String nombreCategoria, CategoriaDAO categoriaDAO) {
        List<Categoria> lista = categoriaDAO.listar();
        for (Categoria c : lista) {
            if (c.getNombre().equals(nombreCategoria)) {
                return c.getIdCategoria();
            }
        }
        return -1;
    }

    public static String obtenerNombreProveedor(Producto producto, ProveedorDAO proveedorDAO) {
        Proveedor proveedor = proveedorDAO.buscarPorId(producto.getIdProveedor());
        return proveedor != null ? proveedor.getNombre() : "Proveedor no encontrado";
    }

    public static String obtenerIdProveedor(String nombreProveedor, ProveedorDAO proveedorDAO) {
        List<Proveedor> lista = proveedorDAO.listar();
        for (Proveedor p : lista) {
            if (p.getNombre().equals(nombreProveedor)) {
                return p.getIdProveedor();
            }
        }
        return null;
    }

    public static int obtenerIdProducto(String nombre, ProductoDAO productoDAO) {
        List<Producto> lista = productoDAO.listar();
        for (Producto p : lista) {
            if (p.getNombre().equals(nombre)) {
                return p.getIdProducto();
            }
        }
        return -1;
    }
}
