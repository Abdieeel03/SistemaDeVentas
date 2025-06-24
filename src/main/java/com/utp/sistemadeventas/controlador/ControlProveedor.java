package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.estructuras.*;
import com.utp.sistemadeventas.modelos.*;
import com.utp.sistemadeventas.util.*;
import com.utp.sistemadeventas.vistas.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author marce
 */
public class ControlProveedor {

    private void showNuevoProveedor() {
        nuevoProveedor.limpiar();
        vtnInicio.mostrarPanel(nuevoProveedor);
    }

    private void showBuscarProveedor() {
        llenarTabla();
        buscarProveedor.txtBusqueda.setText("");
        vtnInicio.mostrarPanel(buscarProveedor);
    }

    private void showEditarProveedor(int fila) {
        editarProveedor.limpiar();
        Proveedor p = this.lista.obtener(fila);
        llenarDatosEditar(p);
        vtnInicio.mostrarPanel(editarProveedor);
    }

    private void accionCancelar() {
        vtnInicio.mostrarPanel(panelProveedor);
    }

    private void registrar() {
        if (nuevoProveedor.txtNombre.getText().isEmpty() || nuevoProveedor.txtRUC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el RUC y Nombre!", "AVISO", JOptionPane.ERROR_MESSAGE);
            nuevoProveedor.txtNombre.requestFocus();
            return;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(nuevoProveedor.txtRUC.getText());
        proveedor.setNombre(nuevoProveedor.txtNombre.getText());
        proveedor.setDireccion(nuevoProveedor.txtDireccion.getText());
        proveedor.setTelefono(nuevoProveedor.txtTelefono.getText());
        proveedor.setPaginaWeb(nuevoProveedor.txtPagina.getText());

        proveedorDAO.agregar(proveedor);
        vtnInicio.mostrarPanel(panelProveedor);
        JOptionPane.showMessageDialog(vtnInicio, "Proveedor creado correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarRegistro() {
        if (editarProveedor.txtNombre.getText().isEmpty() || editarProveedor.txtRUC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el RUC y Nombre!", "AVISO", JOptionPane.ERROR_MESSAGE);
            editarProveedor.txtNombre.requestFocus();
            return;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(editarProveedor.txtRUC.getText());
        proveedor.setNombre(editarProveedor.txtNombre.getText());
        proveedor.setDireccion(editarProveedor.txtDireccion.getText());
        proveedor.setTelefono(editarProveedor.txtTelefono.getText());
        proveedor.setPaginaWeb(editarProveedor.txtPagina.getText());

        proveedorDAO.actualizar(proveedor);
        JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarRegistro() {
        int opcion = JOptionPane.showConfirmDialog(vtnInicio,
                "¿Estás seguro de querer eliminar este proveedor?",
                "AVISO",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            Proveedor proveedorAEliminar = lista.obtenerActual();
            proveedorDAO.eliminar(proveedorAEliminar.getIdProveedor());
            if (!lista.estaVacia()) {
                llenarDatosEditar(lista.obtenerActual());
            } else {
                vtnInicio.mostrarPanel(panelProveedor);
                JOptionPane.showMessageDialog(null, "No quedan proveedores en la lista", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void buscarRegistro() {
        DefaultTableModel model = (DefaultTableModel) buscarProveedor.tableProveedor.getModel();
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) buscarProveedor.tableProveedor.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(model);
            buscarProveedor.tableProveedor.setRowSorter(sorter);
        }
        String textoBusqueda = buscarProveedor.txtBusqueda.getText().trim();
        if (textoBusqueda.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, 0));
        }
    }

    private ListaEnlazadaDoble<Proveedor> llenarListaEnlazada() {
        this.lista = new ListaEnlazadaDoble();
        for (Proveedor p : proveedorDAO.listar()) {
            lista.agregar(p);
        }
        return lista;
    }

    private void llenarTabla() {
        DefaultTableModel model = (DefaultTableModel) buscarProveedor.tableProveedor.getModel();
        model.setRowCount(0);
        this.lista = llenarListaEnlazada();
        for (Proveedor p : proveedorDAO.listar()) {
            Object[] object = new Object[5];
            object[0] = p.getIdProveedor();
            object[1] = p.getNombre();
            object[2] = p.getDireccion();
            object[3] = p.getTelefono();
            object[4] = p.getPaginaWeb();
            model.addRow(object);
        }
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        buscarProveedor.tableProveedor.setRowSorter(sorter);
    }

    private void llenarDatosEditar(Proveedor p) {
        editarProveedor.txtRUC.setText(p.getIdProveedor());
        editarProveedor.txtNombre.setText(p.getNombre());
        editarProveedor.txtDireccion.setText(p.getDireccion());
        editarProveedor.txtTelefono.setText(p.getTelefono());
        editarProveedor.txtPagina.setText(p.getDireccion());
    }

    private void anteriorRegistro() {
        if (lista.anterior()) {
            Proveedor p = lista.obtenerActual();
            llenarDatosEditar(p);
            return;
        }
        JOptionPane.showMessageDialog(null, "Ya estás en el primer registro", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void siguienteRegistro() {
        if (lista.siguiente()) {
            Proveedor p = lista.obtenerActual();
            llenarDatosEditar(p);
            return;
        }
        JOptionPane.showMessageDialog(null, "Ya estás en el último registro", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /*Declaracion de variables*/
    private VtnInicio vtnInicio;
    private PanelProveedor panelProveedor;
    private BuscarProveedor buscarProveedor;
    private NuevoProveedor nuevoProveedor;
    private EditarProveedor editarProveedor;
    private ProveedorDAO proveedorDAO;
    //Lista Enlazada
    private ListaEnlazadaDoble<Proveedor> lista;

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
        editarProveedor.btnAnterior.addActionListener(e -> anteriorRegistro());
        editarProveedor.btnSiguiente.addActionListener(e -> siguienteRegistro());
    }
}
