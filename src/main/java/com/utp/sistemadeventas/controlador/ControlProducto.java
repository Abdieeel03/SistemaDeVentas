package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.estructuras.ListaEnlazadaDoble;
import com.utp.sistemadeventas.vistas.*;
import com.utp.sistemadeventas.modelos.*;
import com.utp.sistemadeventas.util.ProductoHelper;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
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
public class ControlProducto {

    private void showNuevoProducto() {
        nuevoProducto.limpiar();
        llenarComboBox();
        vtnInicio.mostrarPanel(nuevoProducto);
    }

    private void showBuscarProducto() {
        llenarTabla();
        buscarProducto.txtBusqueda.setText("");
        vtnInicio.mostrarPanel(buscarProducto);
    }

    private void showEditarProducto(int fila) {
        Producto p = this.lista.obtener(fila);
        llenarComboBox();
        llenarDatosEditar(p);
        vtnInicio.mostrarPanel(editarProducto);
    }

    private void registrar() {
        if (nuevoProducto.txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nombre!", "AVISO", JOptionPane.ERROR_MESSAGE);
            nuevoProducto.txtNombre.requestFocus();
            return;
        }
        if (nuevoProducto.txtPrecioCompra.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Precio de Compra!", "AVISO", JOptionPane.ERROR_MESSAGE);
            nuevoProducto.txtPrecioCompra.requestFocus();
            return;
        }
        if (nuevoProducto.txtPrecioVenta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Precio de Venta!", "AVISO", JOptionPane.ERROR_MESSAGE);
            nuevoProducto.txtPrecioVenta.requestFocus();
            return;
        }
        if ((int) nuevoProducto.spnStock.getValue() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Stock!", "AVISO", JOptionPane.ERROR_MESSAGE);
            nuevoProducto.spnStock.requestFocus();
            return;
        }
        if (nuevoProducto.cmbCategoria.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione la categoria!", "AVISO", JOptionPane.ERROR_MESSAGE);
            nuevoProducto.cmbCategoria.requestFocus();
            return;
        }
        if (nuevoProducto.cmbProveedor.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Proveedor", "AVISO", JOptionPane.ERROR_MESSAGE);
            nuevoProducto.cmbProveedor.requestFocus();
            return;
        }
        Producto p = new Producto();
        p.setNombre(nuevoProducto.txtNombre.getText());
        p.setPrecioCompra(Double.parseDouble(nuevoProducto.txtPrecioCompra.getText()));
        p.setPrecioVenta(Double.parseDouble(nuevoProducto.txtPrecioVenta.getText()));
        p.setStock(Integer.parseInt(nuevoProducto.spnStock.getValue().toString()));
        p.setIdCategoria(ProductoHelper.obtenerIdCategoria(nuevoProducto.cmbCategoria.getSelectedItem().toString(), categoriaDAO));
        p.setIdProveedor(ProductoHelper.obtenerIdProveedor(nuevoProducto.cmbProveedor.getSelectedItem().toString(), proveedorDAO));

        productoDAO.agregar(p);
        vtnInicio.mostrarPanel(panelProductos);
        JOptionPane.showMessageDialog(vtnInicio, "Producto registrado correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void accionCancelar() {
        vtnInicio.mostrarPanel(panelProductos);
    }

    private void actualizarRegistro() {
        if (editarProducto.txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Nombre!", "AVISO", JOptionPane.ERROR_MESSAGE);
            editarProducto.txtNombre.requestFocus();
            return;
        }
        if (editarProducto.txtPrecioCompra.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Precio de Compra!", "AVISO", JOptionPane.ERROR_MESSAGE);
            editarProducto.txtPrecioCompra.requestFocus();
            return;
        }
        if (editarProducto.txtPrecioVenta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Precio de Venta!", "AVISO", JOptionPane.ERROR_MESSAGE);
            editarProducto.txtPrecioVenta.requestFocus();
            return;
        }
        if ((int) editarProducto.spnStock.getValue() == 0) {
            JOptionPane.showMessageDialog(null, "Ingrese el Stock!", "AVISO", JOptionPane.ERROR_MESSAGE);
            editarProducto.spnStock.requestFocus();
            return;
        }
        if (editarProducto.cmbCategoria.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione la categoria!", "AVISO", JOptionPane.ERROR_MESSAGE);
            editarProducto.cmbCategoria.requestFocus();
            return;
        }
        if (editarProducto.cmbProveedor.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Proveedor", "AVISO", JOptionPane.ERROR_MESSAGE);
            editarProducto.cmbProveedor.requestFocus();
            return;
        }
        
        int idProducto = Integer.parseInt(editarProducto.txtID.getText());
        String nombreCategoria = editarProducto.cmbCategoria.getSelectedItem().toString();
        String nombreProveedor = editarProducto.cmbProveedor.getSelectedItem().toString();
        
        Producto productoActualizado = new Producto();
        productoActualizado.setIdProducto(idProducto);
        productoActualizado.setNombre(editarProducto.txtNombre.getText());
        productoActualizado.setPrecioCompra(Double.parseDouble(editarProducto.txtPrecioCompra.getText()));
        productoActualizado.setPrecioVenta(Double.parseDouble(editarProducto.txtPrecioVenta.getText()));
        productoActualizado.setStock(Integer.parseInt(editarProducto.spnStock.getValue().toString()));
        productoActualizado.setIdCategoria(ProductoHelper.obtenerIdCategoria(nombreCategoria, categoriaDAO));
        productoActualizado.setIdProveedor(ProductoHelper.obtenerIdProveedor(nombreProveedor, proveedorDAO));

        productoDAO.actualizar(productoActualizado);
        for (int i = 0; i < lista.tamanio(); i++) {
            Producto p = lista.obtener(i);
            if (p.getIdProducto() == idProducto) {
                lista.set(i, productoActualizado);
                break;
            }
        }
        llenarDatosEditar(lista.obtenerActual());
        JOptionPane.showMessageDialog(vtnInicio, "Producto actualizado correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarRegistro() {
        int opcion = JOptionPane.showConfirmDialog(vtnInicio,
                "¿Estás seguro de eliminar este usuario?",
                "AVISO",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            Producto productoAEliminar = lista.obtenerActual();
            productoDAO.eliminar(String.valueOf(productoAEliminar.getIdProducto()));
            lista.eliminar(productoAEliminar);

            if (!lista.estaVacia()) {
                llenarDatosEditar(lista.obtenerActual());
            } else {
                vtnInicio.mostrarPanel(panelProductos);
                JOptionPane.showMessageDialog(null, "No quedan usuarios en la lista", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            }

            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void anteriorRegistro() {
        if (lista.anterior()) {
            Producto p = lista.obtenerActual();
            llenarDatosEditar(p);
            return;
        }
        JOptionPane.showMessageDialog(null, "Ya estás en el primer registro", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void siguienteRegistro() {
        if (lista.siguiente()) {
            Producto p = lista.obtenerActual();
            llenarDatosEditar(p);
            return;
        }
        JOptionPane.showMessageDialog(null, "Ya estás en el último registro", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void llenarComboBox() {
        List<Categoria> categorias = categoriaDAO.listar();
        List<Proveedor> proveedores = proveedorDAO.listar();

        nuevoProducto.cmbCategoria.removeAllItems();
        nuevoProducto.cmbProveedor.removeAllItems();
        editarProducto.cmbCategoria.removeAllItems();
        editarProducto.cmbProveedor.removeAllItems();

        for (Categoria c : categorias) {
            nuevoProducto.cmbCategoria.addItem(c.getNombre());
            editarProducto.cmbCategoria.addItem(c.getNombre());
        }

        for (Proveedor p : proveedores) {
            nuevoProducto.cmbProveedor.addItem(p.getNombre());
            editarProducto.cmbProveedor.addItem(p.getNombre());
        }

    }

    private ListaEnlazadaDoble<Producto> llenarListaEnlazada() {
        this.lista = new ListaEnlazadaDoble();
        for (Producto p : productoDAO.listar()) {
            lista.agregar(p);
        }
        return lista;
    }

    private void llenarTabla() {
        DefaultTableModel model = (DefaultTableModel) buscarProducto.table.getModel();
        model.setRowCount(0);
        this.lista = llenarListaEnlazada();
        for (Producto p : productoDAO.listar()) {
            Object[] object = new Object[7];
            object[0] = p.getIdProducto();
            object[1] = p.getNombre();
            object[2] = p.getPrecioCompra();
            object[3] = p.getPrecioVenta();
            object[4] = p.getStock();
            object[5] = ProductoHelper.obtenerNombreCategoria(p, categoriaDAO);
            object[6] = ProductoHelper.obtenerNombreProveedor(p, proveedorDAO);
            model.addRow(object);
        }
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        buscarProducto.table.setRowSorter(sorter);
    }

    private void llenarDatosEditar(Producto p) {
        editarProducto.txtID.setText(String.valueOf(p.getIdProducto()));
        editarProducto.txtNombre.setText(p.getNombre());
        editarProducto.txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
        editarProducto.txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
        editarProducto.spnStock.setValue(p.getStock());
        editarProducto.cmbCategoria.setSelectedItem(ProductoHelper.obtenerNombreCategoria(p, categoriaDAO));
        editarProducto.cmbProveedor.setSelectedItem(ProductoHelper.obtenerNombreProveedor(p, proveedorDAO));
    }

    private void buscarRegistro() {
        DefaultTableModel model = (DefaultTableModel) buscarProducto.table.getModel();
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) buscarProducto.table.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(model);
            buscarProducto.table.setRowSorter(sorter);
        }
        String textoBusqueda = buscarProducto.txtBusqueda.getText().trim();
        if (textoBusqueda.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, 1));
        }
    }

    /*Declaracion de variables*/
    private final VtnInicio vtnInicio;
    private final PanelProductos panelProductos;
    private final BuscarProducto buscarProducto;
    private final NuevoProducto nuevoProducto;
    private final EditarProducto editarProducto;
    private final ProductoDAO productoDAO;
    private final ProveedorDAO proveedorDAO;
    private final CategoriaDAO categoriaDAO;
    //Lista Enlazada
    private ListaEnlazadaDoble<Producto> lista;

    /*Constructor*/
    public ControlProducto(VtnInicio vtnInicio, PanelProductos panelProductos, BuscarProducto buscarProducto, NuevoProducto nuevoProducto, EditarProducto editarProducto, ProductoDAO productoDAO, ProveedorDAO proveedorDAO, CategoriaDAO categoriaDAO) {
        this.vtnInicio = vtnInicio;
        this.panelProductos = panelProductos;
        this.buscarProducto = buscarProducto;
        this.nuevoProducto = nuevoProducto;
        this.editarProducto = editarProducto;
        this.productoDAO = productoDAO;
        this.proveedorDAO = proveedorDAO;
        this.categoriaDAO = categoriaDAO;

        panelProductos.btnNuevoProducto.addActionListener(e -> showNuevoProducto());
        panelProductos.btnBuscarProducto.addActionListener(e -> showBuscarProducto());
        nuevoProducto.btnRegistrar.addActionListener(e -> registrar());
        nuevoProducto.btnCancelar.addActionListener(e -> accionCancelar());
        buscarProducto.btnCancelar.addActionListener(e -> accionCancelar());
        buscarProducto.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = buscarProducto.table.getSelectedRow();
                    showEditarProducto(fila);
                }
            }
        });
        buscarProducto.txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
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
        editarProducto.btnActualizar.addActionListener(e -> actualizarRegistro());
        editarProducto.btnEliminar.addActionListener(e -> eliminarRegistro());
        editarProducto.btnCancelar.addActionListener(e -> accionCancelar());
        editarProducto.btnAnterior.addActionListener(e -> anteriorRegistro());
        editarProducto.btnSiguiente.addActionListener(e -> siguienteRegistro());
    }

}
