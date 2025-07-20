package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.DetalleVentaDAO;
import com.utp.sistemadeventas.dao.ProductoDAO;
import com.utp.sistemadeventas.dao.VentaDAO;
import com.utp.sistemadeventas.modelos.DetalleVenta;
import com.utp.sistemadeventas.modelos.Producto;
import com.utp.sistemadeventas.modelos.Venta;
import com.utp.sistemadeventas.vistas.ReporteDeVentas;
import com.utp.sistemadeventas.vistas.VtnInicio;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marce
 */
public class ControlReporte {

    private void cargarProductosMasVendidos() {
        DefaultTableModel modelo = (DefaultTableModel) panelReporte.tableMasVendidos.getModel();
        modelo.setRowCount(0);

        ArrayList<Object[]> listaFilas = new ArrayList<>();

        for (Producto p : productoDAO.listar()) {
            int totalVendido = 0;

            for (DetalleVenta dv : detalleVentaDAO.listar()) {
                if (dv.getIdProducto() == p.getIdProducto()) {
                    totalVendido += dv.getCantidad();
                }
            }

            if (totalVendido > 0) {
                Object[] fila = new Object[3];
                fila[0] = p.getNombre();
                fila[1] = totalVendido;
                fila[2] = p.getStock();
                listaFilas.add(fila);
            }
        }

        for (int i = 0; i < listaFilas.size() - 1; i++) {
            for (int j = i + 1; j < listaFilas.size(); j++) {
                int cantidadI = (int) listaFilas.get(i)[1];
                int cantidadJ = (int) listaFilas.get(j)[1];

                if (cantidadJ > cantidadI) {
                    Object[] temp = listaFilas.get(i);
                    listaFilas.set(i, listaFilas.get(j));
                    listaFilas.set(j, temp);
                }
            }
        }

        for (Object[] fila : listaFilas) {
            modelo.addRow(fila);
        }
    }

    private void cargarUltimasVentas() {
        DefaultTableModel ultimosVendidos = (DefaultTableModel) panelReporte.tableUltimas.getModel();
        ultimosVendidos.setRowCount(0);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        LinkedList<Object[]> pila = new LinkedList();
        for (Venta v : ventaDAO.listar()) {
            for (DetalleVenta dv : detalleVentaDAO.listar()) {
                if (v.getId_venta() == dv.getIdVenta()) {
                    Object[] object = new Object[4];
                    object[0] = productoDAO.buscarPorId(String.valueOf(dv.getIdProducto())).getNombre();
                    object[1] = formatoFecha.format(v.getFechaVenta());
                    object[2] = dv.getCantidad();
                    object[3] = dv.getSubtotal();
                    pila.push(object);
                }
            }
        }
        for(Object[] item : pila){
            ultimosVendidos.addRow(item);
        }
    }

    public void cargarEstadisticas() {
        cargarUltimasVentas();
        cargarProductosMasVendidos();
    }

    /*Declaracion de variables*/
    private VtnInicio vtnInicio;
    private ReporteDeVentas panelReporte;
    private ProductoDAO productoDAO;
    private DetalleVentaDAO detalleVentaDAO;
    private VentaDAO ventaDAO;

    public ControlReporte(VtnInicio vtnInicio, ReporteDeVentas panelReporte, ProductoDAO productoDAO, DetalleVentaDAO detalleVentaDAO, VentaDAO ventaDAO) {
        this.vtnInicio = vtnInicio;
        this.panelReporte = panelReporte;
        this.productoDAO = productoDAO;
        this.detalleVentaDAO = detalleVentaDAO;
        this.ventaDAO = ventaDAO;
    }

}
