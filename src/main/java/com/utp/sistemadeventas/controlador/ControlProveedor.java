package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.estructuras.*;
import com.utp.sistemadeventas.modelos.*;
import com.utp.sistemadeventas.util.*;
import com.utp.sistemadeventas.vistas.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author marce
 */
public class ControlProveedor {

    private void showNuevoProveedor() {

    }

    private void showBuscarProveedor() {

    }

    private void showEditarProveedor(int fila) {

    }

    private void accionCancelar() {

    }

    private void registrar() {

    }

    private void actualizarRegistro() {

    }

    private void eliminarRegistro() {

    }

    private void buscarRegistro() {

    }

    private void llenarListaEnlazada() {

    }

    private void llenarTabla() {

    }

    private void llenarDatosEditar(Proveedor p) {

    }

    /*Declaracion de variables*/
    private VtnInicio vtnInicio;
    private PanelProveedor panelProveedor;
    private BuscarProveedor buscarProveedor;
    private NuevoProveedor nuevoProveedor;
    private EditarProveedor editarProveedor;
    private ProveedorDAO proveedorDAO;

    /*Constructor*/
    public ControlProveedor(VtnInicio vtnInicio, PanelProveedor panelProveedor, BuscarProveedor buscarProveedor, NuevoProveedor nuevoProveedor, EditarProveedor editarProveedor, ProveedorDAO proveedorDAO) {
        this.vtnInicio = vtnInicio;
        this.panelProveedor = panelProveedor;
        this.buscarProveedor = buscarProveedor;
        this.nuevoProveedor = nuevoProveedor;
        this.editarProveedor = editarProveedor;
        this.proveedorDAO = proveedorDAO;
        panelProveedor.btnNuevoProveedor.addActionListener(e -> showNuevoProveedor());
        panelProveedor.btnBuscarProveedor.addActionListener(e -> showBuscarProveedor());
        nuevoProveedor.btnSalir.addActionListener(e -> accionCancelar());
        nuevoProveedor.btnRegistrar.addActionListener(e -> registrar());
        buscarProveedor.btnCancelar.addActionListener(e -> accionCancelar());
        buscarProveedor.tableProveedor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = buscarProveedor.tableProveedor.getSelectedRow();
                    showEditarProveedor(fila);
                }
            }
        });
        buscarProveedor.txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                buscarRegistro();
            }

            public void removeUpdate(DocumentEvent e) {
                buscarRegistro();
            }

            public void changedUpdate(DocumentEvent e) {
                buscarRegistro();
            }

        });
        editarProveedor.btnActualizar.addActionListener(e -> actualizarRegistro());
        editarProveedor.btnEliminar.addActionListener(e -> eliminarRegistro());
        editarProveedor.btnSalir.addActionListener(e -> accionCancelar());
    }
}
