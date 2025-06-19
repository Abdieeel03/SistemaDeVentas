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

    public static void mostrarProductos(ProductoDAO productoDAO, CategoriaDAO categoriaDAO, ProveedorDAO proveedorDAO) {
        System.out.println("Lista de Productos:");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-30s %-20s %-25s %-10s %-10s %-6s\n", "ID", "Nombre", "Categoría", "Proveedor", "P. Compra", "P. Venta", "Stock");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        for (Producto p : productoDAO.listar()) {
            String categoria = obtenerNombreCategoria(p, categoriaDAO);
            String proveedor = obtenerNombreProveedor(p, proveedorDAO);
            System.out.printf("%-5d %-30s %-20s %-25s %-10.2f %-10.2f %-6d\n",
                    p.getIdProducto(),
                    p.getNombre(),
                    categoria,
                    proveedor,
                    p.getPrecioCompra(),
                    p.getPrecioVenta(),
                    p.getStock());
        }

        System.out.println("---------------------------------------------------------------------------------------------------------");
    }
}
