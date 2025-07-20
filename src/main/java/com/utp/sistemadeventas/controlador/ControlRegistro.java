package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.modelos.*;
import com.utp.sistemadeventas.util.ClienteHelper;
import com.utp.sistemadeventas.util.DetalleVentaHelper;
import com.utp.sistemadeventas.util.ProductoHelper;
import com.utp.sistemadeventas.vistas.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author marce
 */
public class ControlRegistro {

    public void seleccionarProducto(int fila) {
        llenarDatosProducto(fila);
        vtnSeleccionar.setVisible(false);
    }

    public void buscarProducto() {
        vtnSeleccionar.txtBusqueda.setText("");
        llenarTablaSeleccionar();
        vtnSeleccionar.setVisible(true);
    }

    public void botonCancelar() {
        if (pila.isEmpty()) {
            JOptionPane.showMessageDialog(vtnInicio, "No hay productos en la venta actual.", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            vtnInicio.mostrarPanel(new PanelPrincipal());
            panelRegistrarVenta.txtNombreProducto.setText("");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vtnInicio,
                "¿Estás seguro de que deseas cancelar toda la venta?",
                "Cancelar Venta", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            pila.clear();
            llenarTablaVenta();
            calcularTotal();
            panelRegistrarVenta.limpiar(); // ya limpia todos los campos del formulario
        }
        vtnInicio.mostrarPanel(new PanelPrincipal());
    }

    public void llenarComboBox() {
        List<MedioPago> mediosPago = medioPagoDAO.listar();
        panelRegistrarVenta.cmbMedioPago.removeAllItems();
        for (MedioPago m : mediosPago) {
            panelRegistrarVenta.cmbMedioPago.addItem(m.getNombre());
        }
    }

    public void vaciarTabla() {
        DefaultTableModel model = (DefaultTableModel) panelRegistrarVenta.tblCompra.getModel();
        model.setRowCount(0);
    }

    public void llenarTablaVenta() {
        DefaultTableModel model = (DefaultTableModel) panelRegistrarVenta.tblCompra.getModel();
        model.setRowCount(0);

        for (Object[] producto : pila) {
            model.addRow(producto);
        }
    }

    public void llenarTablaSeleccionar() {
        DefaultTableModel model = (DefaultTableModel) vtnSeleccionar.tblProducto.getModel();
        model.setRowCount(0);

        for (Producto p : productoDAO.listar()) {
            Object[] object = new Object[4];
            object[0] = p.getIdProducto();
            object[1] = p.getNombre();
            object[2] = p.getPrecioVenta();
            object[3] = p.getStock();
            model.addRow(object);
        }

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        vtnSeleccionar.tblProducto.setRowSorter(sorter);
    }

    public void llenarDatosProducto(int fila) {
        panelRegistrarVenta.txtNombreProducto.setText(String.valueOf(vtnSeleccionar.tblProducto.getValueAt(fila, 1)));
        panelRegistrarVenta.txtPrecio.setText(String.valueOf(vtnSeleccionar.tblProducto.getValueAt(fila, 2)));
    }

    public void buscarRegistro() {
        DefaultTableModel model = (DefaultTableModel) vtnSeleccionar.tblProducto.getModel();
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) vtnSeleccionar.tblProducto.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(model);
            vtnSeleccionar.tblProducto.setRowSorter(sorter);
        }
        String textoBusqueda = vtnSeleccionar.txtBusqueda.getText().trim();
        if (textoBusqueda.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, 1));
        }
    }

    public void agregarProducto() {
        String nombre = panelRegistrarVenta.txtNombreProducto.getText();
        int cantidad = (int) panelRegistrarVenta.spnCantidad.getValue();
        double precio = Double.parseDouble(panelRegistrarVenta.txtPrecio.getText());
        double subtotal = cantidad * precio;

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vtnInicio, "No ha seleccionado ningún producto!", "AVISO", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidad == 0) {
            JOptionPane.showMessageDialog(vtnInicio, "Seleccione la cantidad de productos!", "AVISO", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean actualizado = false;
        for (Object[] item : pila) {
            if (item[0].equals(nombre)) {
                int nuevaCantidad = (int) item[1] + cantidad;
                double nuevoSubtotal = nuevaCantidad * precio;
                item[1] = nuevaCantidad;
                item[3] = String.format("%.2f", nuevoSubtotal);
                actualizado = true;
                break;
            }
        }

        if (!actualizado) {
            Object[] producto = new Object[4];
            producto[0] = nombre;
            producto[1] = cantidad;
            producto[2] = String.format("%.2f", precio);
            producto[3] = String.format("%.2f", subtotal);
            pila.push(producto);
        }
        //FALTA VALIDAR QUE LA CANTIDAD INGRESADA NO SUPERE EL STOCK
        llenarTablaVenta();
        calcularTotal();

        panelRegistrarVenta.txtNombreProducto.setText("");
        panelRegistrarVenta.txtPrecio.setText("");
        panelRegistrarVenta.txtSubtotal.setText("");
        panelRegistrarVenta.spnCantidad.setValue(0);
    }

    public void eliminarProducto() {
        if (pila.isEmpty()) {
            JOptionPane.showMessageDialog(vtnInicio, "No hay productos para eliminar.", "AVISO", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object[] producto = pila.peek();
        int confirm = JOptionPane.showConfirmDialog(vtnInicio,
                "¿Deseas eliminar el último producto agregado?\nProducto: " + producto[0],
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            pila.pop();
            llenarTablaVenta();
            calcularTotal();
        }
    }

    public void calcularTotal() {
        DefaultTableModel model = (DefaultTableModel) panelRegistrarVenta.tblCompra.getModel();
        double subtotales = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Object subtotal = model.getValueAt(i, 3);
            if (subtotal != null) {
                subtotales += Double.parseDouble(subtotal.toString());
            }
        }
        panelRegistrarVenta.lblSubTotal.setText(String.format("%.2f", subtotales));
        Double igv = subtotales * 0.18;
        panelRegistrarVenta.lblIGV.setText(String.format("%.2f", igv));
        Double total = subtotales + igv;
        panelRegistrarVenta.lblTotal.setText(String.format("%.2f", total));
    }

    public void calcularCambio() {
        if (panelRegistrarVenta.txtDineroRecibido.getText().isEmpty()) {
            panelRegistrarVenta.txtCambio.setText("");
            return;
        }
        if (panelRegistrarVenta.lblTotal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ningun producto seleccionado", "AVISO", JOptionPane.ERROR_MESSAGE);
            panelRegistrarVenta.txtDineroRecibido.setText("");
            return;
        }
        try {
            Double total = Double.valueOf(panelRegistrarVenta.lblTotal.getText());
            System.out.println(total);
            Double dineroRecibido = Double.valueOf(panelRegistrarVenta.txtDineroRecibido.getText());
            System.out.println(dineroRecibido);
            Double cambio = dineroRecibido - total;
            System.out.println(cambio);
            panelRegistrarVenta.txtCambio.setText(String.valueOf(String.format("%.2f", cambio)));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número correcto", "AVISO", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void desbloquearCambio(String item) {
        switch (item) {
            case "Billetera digital":
                this.idMedioPago = 2;
                panelRegistrarVenta.txtDineroRecibido.setText("");
                panelRegistrarVenta.txtCambio.setText("");
                panelRegistrarVenta.txtDineroRecibido.setEditable(false);
                break;
            case "Efectivo":
                this.idMedioPago = 1;
                panelRegistrarVenta.txtDineroRecibido.setText("");
                panelRegistrarVenta.txtDineroRecibido.setEditable(true);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void limpiarDatos() {
        panelRegistrarVenta.limpiar();
    }

    public void registrarVenta() {
        DefaultTableModel model = (DefaultTableModel) panelRegistrarVenta.tblCompra.getModel();

        //Registrar Cliente
        Cliente c = guardarCliente();
        System.out.println(ClienteHelper.existe(c, clienteDAO));
        if (!ClienteHelper.existe(c, clienteDAO)) {
            clienteDAO.agregar(c);
        }

        //Obtener datos de la venta
        Venta v = new Venta(0, new Date(), 0.0, this.idMedioPago, c.getId_cliente());
        v.setTotal(Double.parseDouble(panelRegistrarVenta.lblTotal.getText()));
        ventaDAO.agregar(v);

        int idVenta = v.getId_venta();
        DetalleVenta detalle;
        for (Object fila : model.getDataVector()) {
            //Guardar detalle
            Vector<?> vectorFila = (Vector<?>) fila;
            int idProducto = ProductoHelper.obtenerIdProducto(String.valueOf(vectorFila.get(0)), productoDAO);
            int cantidad = Integer.parseInt(String.valueOf(vectorFila.get(1)));
            detalle = DetalleVentaHelper.crearDetalle(idVenta, idProducto, cantidad, productoDAO);
            detalleDAO.agregar(detalle);
            //Actualizar stock
            Producto p = productoDAO.buscarPorId(String.valueOf(idProducto));
            int stockActual = p.getStock();
            int nuevoStock = stockActual - cantidad;
            p.setStock(nuevoStock);
            productoDAO.actualizar(p);
        }

        //Limpiar pila y tabla
        pila.clear();
        llenarTablaVenta();
        calcularTotal();
        panelRegistrarVenta.limpiar();

    }

    public void btnRegistrar() {
        DefaultTableModel model = (DefaultTableModel) panelRegistrarVenta.tblCompra.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No ha seleccionado ningun producto!", "AVISO", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(vtnInicio,
                "¿Deseas registrar la venta?",
                "Confirmar registro", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            registrarVenta();
        }
    }

    public Cliente guardarCliente() {
        Cliente c = new Cliente();
        if (panelRegistrarVenta.txtDocumento.getText().isEmpty() && panelRegistrarVenta.txtNombreCliente.getText().isEmpty()) {
            c.setId_cliente("00000000");
            c.setNombre("Cliente Default");
            return c;
        }
        if (panelRegistrarVenta.txtNombreCliente.getText().isEmpty()) {
            c.setId_cliente(panelRegistrarVenta.txtDocumento.getText());
            c.setNombre("Sin nombre");
            return c;
        }
        c.setId_cliente(panelRegistrarVenta.txtDocumento.getText());
        c.setNombre(panelRegistrarVenta.txtNombreCliente.getText());
        return c;
    }

    /*Declaracion de variables*/
    private VtnInicio vtnInicio;
    private VtnSeleccionarProducto vtnSeleccionar;
    private PanelRegistrarVenta panelRegistrarVenta;
    private ProductoDAO productoDAO;
    private VentaDAO ventaDAO;
    private DetalleVentaDAO detalleDAO;
    private ClienteDAO clienteDAO;
    private MedioPagoDAO medioPagoDAO;
    //Pila
    private LinkedList<Object[]> pila;
    //MedioPago
    private int idMedioPago;

    //Constructor
    public ControlRegistro(VtnInicio vtnInicio, VtnSeleccionarProducto vtnSeleccionar, PanelRegistrarVenta panelRegistrarVenta, ProductoDAO productoDAO, VentaDAO ventaDAO, DetalleVentaDAO detalleDAO, ClienteDAO clienteDAO, MedioPagoDAO medioPagoDAO) {
        this.vtnInicio = vtnInicio;
        this.vtnSeleccionar = vtnSeleccionar;
        this.panelRegistrarVenta = panelRegistrarVenta;
        this.productoDAO = productoDAO;
        this.ventaDAO = ventaDAO;
        this.detalleDAO = detalleDAO;
        this.clienteDAO = clienteDAO;
        this.medioPagoDAO = medioPagoDAO;
        this.pila = new LinkedList<>();
        this.idMedioPago = 1;
        panelRegistrarVenta.txtNombreProducto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buscarProducto();
            }
        });
        panelRegistrarVenta.btnRegistrarVenta.addActionListener(e -> {
            btnRegistrar();
        });
        panelRegistrarVenta.btnCancelar.addActionListener(e -> botonCancelar());
        panelRegistrarVenta.btnEliminar.addActionListener(e -> eliminarProducto());
        panelRegistrarVenta.btnAgregarProducto.addActionListener(e -> agregarProducto());
        panelRegistrarVenta.spnCantidad.addChangeListener((ChangeEvent e) -> {
            if (panelRegistrarVenta.txtNombreProducto.getText().isEmpty()) {
                return;
            }
            int cantidad = (int) panelRegistrarVenta.spnCantidad.getValue();
            if (cantidad != 0) {
                double subtotal = cantidad * Double.parseDouble(panelRegistrarVenta.txtPrecio.getText());
                panelRegistrarVenta.txtSubtotal.setText(String.valueOf(String.format("%.2f", subtotal)));
            }
        });
        vtnSeleccionar.tblProducto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = vtnSeleccionar.tblProducto.getSelectedRow();
                    seleccionarProducto(fila);
                }
            }
        });
        vtnSeleccionar.txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarRegistro();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarRegistro();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarRegistro();
            }

        });
        panelRegistrarVenta.txtDineroRecibido.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calcularCambio();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calcularCambio();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calcularCambio();
            }

        });
        panelRegistrarVenta.cmbMedioPago.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String item = (String) e.getItem();
                System.out.println(item);
                desbloquearCambio(item);
            }
        });
    }

}
