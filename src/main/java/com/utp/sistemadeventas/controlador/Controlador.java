package com.utp.sistemadeventas.controlador;

import com.utp.sistemadeventas.dao.*;
import com.utp.sistemadeventas.vistas.*;
import com.utp.sistemadeventas.modelos.*;
import com.utp.sistemadeventas.util.UsuarioHelper;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author marce
 */
public class Controlador {

    public void iniciarEjecucion(){
        vtnLogin.setVisible(true);
    }
    
    public void iniciarSesion() {
        vtnLogin.setVisible(false);
        vtnInicio.mostrarPanel(new PanelPrincipal());
        vtnInicio.setVisible(true);
    }

    public void showPanelRegistro() {
        vtnInicio.mostrarPanel(panelRegistrarVenta);
    }

    public void showPanelProducto() {
        if (rol.equals("Cajero")) {
            JOptionPane.showMessageDialog(null, "Usted no puede acceder a este apartado!");
            return;
        }
        vtnInicio.mostrarPanel(panelProductos);
    }

    public void showPanelProveedores() {
        if (rol.equals("Cajero")) {
            JOptionPane.showMessageDialog(null, "Usted no puede acceder a este apartado!");
            return;
        }
        vtnInicio.mostrarPanel(panelProveedor);
    }

    public void showPanelUsuarios() {
        if (rol.equals("Cajero") || rol.equals("Moderador")) {
            JOptionPane.showMessageDialog(null, "Usted no puede acceder a este apartado!");
            return;
        }
        vtnInicio.mostrarPanel(panelUsuario);
    }

    public void showPanelReporte() {
        if (rol.equals("Cajero") || rol.equals("Moderador")) {
            JOptionPane.showMessageDialog(null, "Usted no puede acceder a este apartado!");
            return;
        }
        vtnInicio.mostrarPanel(panelReporte);
    }

    public void verificarLogin() {
        if (vtnLogin.txtUsuario.getText().isEmpty() || vtnLogin.txtContra.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Datos Incorrectos!");
            vtnLogin.txtUsuario.requestFocus();
            return;
        }

        List<Usuario> usuarios = usuarioDAO.listar();

        boolean usuarioEncontrado = false;

        for (Usuario user : usuarios) {
            if (user.getUsuario().equals(vtnLogin.txtUsuario.getText())) {
                if (user.getContraseña().equals(vtnLogin.txtContra.getText())) {
                    usuarioEncontrado = true;
                    rol = UsuarioHelper.obtenerNombreRol(user, rolDAO);
                    break;
                }
            }
        }

        if (usuarioEncontrado) {
            iniciarSesion();
        } else {
            JOptionPane.showMessageDialog(null, "Datos Incorrectos!");
        }

    }

    public void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(vtnInicio,
                "¿Estás seguro de querer cerrar sesión?",
                "Confirmar cierre",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            vtnInicio.setVisible(false);
            vtnLogin.limpiar();
            vtnLogin.setVisible(true);
            this.rol = null;
        }
    }

    /*Declaracion de variables*/
    //Ventanas
    private final VtnLogin vtnLogin;
    private final VtnInicio vtnInicio;
    //Registro
    private final PanelRegistrarVenta panelRegistrarVenta;
    private final ControlRegistro controlRegistro;
    //Productos
    private final PanelProductos panelProductos;
    private final BuscarProducto buscarProducto;
    private final NuevoProducto nuevoProducto;
    private final EditarProducto editarProducto;
    private final VtnSeleccionarProducto vtnSeleccionar;
    private final ControlProducto controlProducto;
    //Proveedor
    private final PanelProveedor panelProveedor;
    private final BuscarProveedor buscarProveedor;
    private final NuevoProveedor nuevoProveedor;
    private final EditarProveedor editarProveedor;
    private final ControlProveedor controlProveedor;
    //Usuarios
    private final PanelUsuario panelUsuario;
    private final BuscarUsuario buscarUsuario;
    private final NuevoUsuario nuevoUsuario;
    private final EditarUsuario editarUsuario;
    private final ControlUsuario controlUsuario;
    //Reporte
    private final ReporteDeVentas panelReporte;
    private final ControlReporte controlReporte;
    //Rol
    private String rol;
    //DAO
    private final CategoriaDAO categoriaDAO;
    private final ClienteDAO clienteDAO;
    private final DetalleVentaDAO detalleDAO;
    private final MedioPagoDAO medioPagoDAO;
    private final ProductoDAO productoDAO;
    private final ProveedorDAO proveedorDAO;
    private final RolDAO rolDAO;
    private final UsuarioDAO usuarioDAO;
    private final VentaDAO ventaDAO;

    //Constructor Principal
    public Controlador(VtnInicio vtnInicio, VtnLogin vtnLogin, CategoriaDAO categoriaDAO, ClienteDAO clienteDAO, DetalleVentaDAO detalleDAO, MedioPagoDAO medioPagoDAO, ProductoDAO productoDAO, ProveedorDAO proveedorDAO, RolDAO rolDAO, UsuarioDAO usuarioDAO, VentaDAO ventaDAO) {
        this.vtnInicio = vtnInicio;
        this.vtnLogin = vtnLogin;
        this.categoriaDAO = categoriaDAO;
        this.clienteDAO = clienteDAO;
        this.detalleDAO = detalleDAO;
        this.medioPagoDAO = medioPagoDAO;
        this.productoDAO = productoDAO;
        this.proveedorDAO = proveedorDAO;
        this.rolDAO = rolDAO;
        this.usuarioDAO = usuarioDAO;
        this.ventaDAO = ventaDAO;
        vtnLogin.btnIngresar.addActionListener(e -> verificarLogin());
        vtnInicio.btnGenerarVenta.addActionListener(e -> showPanelRegistro());
        vtnInicio.btnProductos.addActionListener(e -> showPanelProducto());
        vtnInicio.btnProveedores.addActionListener(e -> showPanelProveedores());
        vtnInicio.btnUsuarios.addActionListener(e -> showPanelUsuarios());
        vtnInicio.btnReporteVentas.addActionListener(e -> showPanelReporte());
        vtnInicio.btnCerrarSesion.addActionListener(e -> cerrarSesion());
        vtnLogin.txtUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verificarLogin();
                }
            }
        });
        vtnLogin.txtContra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verificarLogin();
                }
            }
        });

        /*Instanciar demás paneles y controladores*/
        //Registro
        panelRegistrarVenta = new PanelRegistrarVenta();
        vtnSeleccionar = new VtnSeleccionarProducto();
        controlRegistro = new ControlRegistro();
        //Productos
        panelProductos = new PanelProductos();
        nuevoProducto = new NuevoProducto();
        buscarProducto = new BuscarProducto();
        editarProducto = new EditarProducto();
        controlProducto = new ControlProducto(vtnInicio, panelProductos, buscarProducto, nuevoProducto, editarProducto, productoDAO, proveedorDAO, categoriaDAO);
        //Proveedor
        panelProveedor = new PanelProveedor();
        nuevoProveedor = new NuevoProveedor();
        buscarProveedor = new BuscarProveedor();
        editarProveedor = new EditarProveedor();
        controlProveedor = new ControlProveedor();
        //Usuarios
        panelUsuario = new PanelUsuario();
        nuevoUsuario = new NuevoUsuario();
        buscarUsuario = new BuscarUsuario();
        editarUsuario = new EditarUsuario();
        controlUsuario = new ControlUsuario(vtnInicio, panelUsuario, buscarUsuario, nuevoUsuario, editarUsuario, usuarioDAO, rolDAO);
        //Reporte
        panelReporte = new ReporteDeVentas();
        controlReporte = new ControlReporte();
    }
}
