package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.DetalleVentaDAO;
import com.utp.sistemadeventas.dao.ProductoDAO;
import com.utp.sistemadeventas.vistas.ReporteDeVentas;
import com.utp.sistemadeventas.vistas.VtnInicio;

/**
 *
 * @author marce
 */
public class ControlReporte {

    /*Declaracion de variables*/
    private VtnInicio vtnInicio;
    private ReporteDeVentas panelReporte;
    private ProductoDAO productoDAO;
    private DetalleVentaDAO detalleVentaDAO;

    public ControlReporte(VtnInicio vtnInicio, ReporteDeVentas panelReporte, ProductoDAO productoDAO, DetalleVentaDAO detalleVentaDAO) {
        this.vtnInicio = vtnInicio;
        this.panelReporte = panelReporte;
        this.productoDAO = productoDAO;
        this.detalleVentaDAO = detalleVentaDAO;
    }

}
