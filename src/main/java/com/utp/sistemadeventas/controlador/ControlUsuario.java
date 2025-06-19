/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.vistas.*;
import com.utp.sistemadeventas.modelos.*;
import com.utp.sistemadeventas.util.UsuarioHelper;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ControlUsuario {

    public void showNuevoUsuario() {
        llenarComboBox();
        vtnInicio.mostrarPanel(nuevoUsuario);
    }

    public void showBuscarUsuario() throws Exception {
        llenarTabla();
        buscarUsuario.txtBusqueda.setText("");
        vtnInicio.mostrarPanel(buscarUsuario);
    }

    public void showEditarUsuario(int fila) {
        llenarComboBox();
        llenarDatosEditar(fila);
        vtnInicio.mostrarPanel(editarUsuario);
    }

    public void accionCancelar() {
        vtnInicio.mostrarPanel(panelUsuario);
    }

    private void buscarRegistro() {
        DefaultTableModel model = (DefaultTableModel) buscarUsuario.table.getModel();
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) buscarUsuario.table.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(model);
            buscarUsuario.table.setRowSorter(sorter);
        }
        String textoBusqueda = buscarUsuario.txtBusqueda.getText().trim();
        if (textoBusqueda.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, 1));
        }
    }

    public void llenarComboBox() {
        try {
            List<Rol> roles = rolDAO.listar();

            nuevoUsuario.cmbRol.removeAllItems();
            editarUsuario.cmbRol.removeAllItems();

            for (Rol rol : roles) {
                nuevoUsuario.cmbRol.addItem(rol.toString());
                editarUsuario.cmbRol.addItem(rol.toString());
            }
        } catch (Exception e) {
        }
    }

    public void llenarTabla() throws Exception {
        DefaultTableModel model = (DefaultTableModel) buscarUsuario.table.getModel();
        model.setRowCount(0);
        for (Usuario u : usuarioDAO.listar()) {
            Object[] object = new Object[4];
            object[0] = u.getIdUsuario();
            object[1] = u.getNombre();
            object[2] = UsuarioHelper.obtenerNombreRol(u, rolDAO);
            object[3] = u.getUsuario();
            model.addRow(object);
        }
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        buscarUsuario.table.setRowSorter(sorter);
    }

    public void llenarDatosEditar(int fila) {
        editarUsuario.txtID.setText((String) buscarUsuario.table.getValueAt(fila, 0));
        editarUsuario.txtNombre.setText((String) buscarUsuario.table.getValueAt(fila, 1));
        editarUsuario.cmbRol.setSelectedItem((String) buscarUsuario.table.getValueAt(fila, 2));
        editarUsuario.txtUsuario.setText((String) buscarUsuario.table.getValueAt(fila, 3));
        editarUsuario.txtContraseña.setText(UsuarioHelper.obtenerContrasena(usuarioDAO.buscarPorId((String) buscarUsuario.table.getValueAt(fila, 0))));
    }

    public void llenarDescripcion(String selectedItem) {
        switch (selectedItem) {
            case "Administrador":
                nuevoUsuario.txtaDescripcion.setText("Control Total del Sistema");
                editarUsuario.txtaDescripcion.setText("Control Total del Sistema");
                break;
            case "Moderador":
                nuevoUsuario.txtaDescripcion.setText("Limitado en la creacion de usuarios");
                editarUsuario.txtaDescripcion.setText("Limitado en la creacion de usuarios");
                break;
            case "Cajero":
                nuevoUsuario.txtaDescripcion.setText("Limitado solo a generar Ventas");
                editarUsuario.txtaDescripcion.setText("Limitado solo a generar Ventas");
                break;
            default:
                nuevoUsuario.txtaDescripcion.setText("Seleccione un rol.");
                break;
        }
    }

    public void registrar() throws Exception {

        if (nuevoUsuario.txtID.getText().isEmpty() || nuevoUsuario.txtNombre.getText().isEmpty() || nuevoUsuario.txtUsuario.getText().isEmpty() || nuevoUsuario.txtContraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese todos los datos!");
            nuevoUsuario.txtNombre.requestFocus();
            return;
        }

        String nombreRol = nuevoUsuario.cmbRol.getSelectedItem().toString();
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(nuevoUsuario.txtID.getText());
        usuario.setIdRol(UsuarioHelper.obtenerIdRol(nombreRol, rolDAO));
        usuario.setNombre(nuevoUsuario.txtNombre.getText());
        usuario.setUsuario(nuevoUsuario.txtUsuario.getText());
        usuario.setContraseña(nuevoUsuario.txtContraseña.getText());
        usuario.setDescripcion(nuevoUsuario.txtaDescripcion.getText());
        usuarioDAO.agregar(usuario);
        vtnInicio.mostrarPanel(panelUsuario);
        nuevoUsuario.limpiar();
        JOptionPane.showMessageDialog(vtnInicio, "Usuario creado correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void actualizarRegistro() {
        if (editarUsuario.txtNombre.getText().isEmpty() || editarUsuario.txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese todos los datos!");
            editarUsuario.txtNombre.requestFocus();
            return;
        }
        String nombreRol = editarUsuario.cmbRol.getSelectedItem().toString();
        Usuario usuario = usuarioDAO.buscarPorId(editarUsuario.txtID.getText());
        usuario.setNombre(editarUsuario.txtNombre.getText());
        usuario.setIdRol(UsuarioHelper.obtenerIdRol(nombreRol, rolDAO));
        usuario.setUsuario(editarUsuario.txtUsuario.getText());
        usuario.setContraseña(editarUsuario.txtContraseña.getText());
        usuario.setDescripcion(editarUsuario.txtaDescripcion.getText());
        usuarioDAO.actualizar(usuario);
        vtnInicio.mostrarPanel(panelUsuario);
        editarUsuario.limpiar();
        JOptionPane.showMessageDialog(vtnInicio, "Usuario actualizado correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }

    public void eliminarRegistro() {
        int opcion = JOptionPane.showConfirmDialog(vtnInicio,
                "¿Estás seguro de eliminar este usuario?",
                "AVISO",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(editarUsuario.txtID.getText());
            vtnInicio.mostrarPanel(panelUsuario);
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            editarUsuario.limpiar();
        }
    }

    //Declaracion de variables
    private VtnInicio vtnInicio;
    private PanelUsuario panelUsuario;
    private BuscarUsuario buscarUsuario;
    private NuevoUsuario nuevoUsuario;
    private EditarUsuario editarUsuario;
    private UsuarioDAO usuarioDAO;
    private RolDAO rolDAO;

    //Controlador
    public ControlUsuario(VtnInicio vtnInicio, PanelUsuario panelUsuario, BuscarUsuario buscarUsuario, NuevoUsuario nuevoUsuario, EditarUsuario editarUsuario, UsuarioDAO usuarioDAO, RolDAO rolDAO) {
        this.vtnInicio = vtnInicio;
        this.panelUsuario = panelUsuario;
        this.buscarUsuario = buscarUsuario;
        this.nuevoUsuario = nuevoUsuario;
        this.editarUsuario = editarUsuario;
        this.usuarioDAO = usuarioDAO;
        this.rolDAO = rolDAO;
        panelUsuario.btnNuevoUsuario.addActionListener(e -> showNuevoUsuario());
        panelUsuario.btnBuscarUsuario.addActionListener(e -> {
            try {
                showBuscarUsuario();
            } catch (Exception ex) {
                Logger.getLogger(ControlUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        nuevoUsuario.btnCancelar.addActionListener(e -> accionCancelar());
        nuevoUsuario.btnRegistrar.addActionListener(e -> {
            try {
                registrar();
            } catch (Exception ex) {
                Logger.getLogger(ControlUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        nuevoUsuario.cmbRol.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    llenarDescripcion(selectedItem);
                }
            }
        });

        buscarUsuario.btnCancelar.addActionListener(e -> accionCancelar());
        buscarUsuario.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = buscarUsuario.table.getSelectedRow();
                    showEditarUsuario(fila);
                }
            }
        });
        buscarUsuario.txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
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

        editarUsuario.cmbRol.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    llenarDescripcion(selectedItem);
                }
            }
        });
        editarUsuario.btnEliminar.addActionListener(e -> eliminarRegistro());
        editarUsuario.btnActualizar.addActionListener(e -> actualizarRegistro());
        editarUsuario.btnCancelar.addActionListener(e -> accionCancelar());
    }
}
