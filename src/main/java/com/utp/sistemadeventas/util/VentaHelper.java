package com.utp.sistemadeventas.util;

/**
 *
 * @author Abdieeel
 */
import com.utp.sistemadeventas.dao.ClienteDAO;
import com.utp.sistemadeventas.dao.MedioPagoDAO;
import com.utp.sistemadeventas.dao.VentaDAO;
import com.utp.sistemadeventas.modelos.Cliente;
import com.utp.sistemadeventas.modelos.MedioPago;
import com.utp.sistemadeventas.modelos.Venta;
import java.text.SimpleDateFormat;

public class VentaHelper {

    public static String obtenerNombreCliente(Venta venta, ClienteDAO clienteDAO) {
        Cliente cliente = clienteDAO.buscarPorId(venta.getIdCliente());
        return cliente != null ? cliente.getNombre() : "Cliente no encontrado";
    }

    public static String obtenerNombreMedioPago(Venta venta, MedioPagoDAO medioPagoDAO) {
        MedioPago medioPago = medioPagoDAO.buscarPorId(String.valueOf(venta.getIdMedioPago()));
        return medioPago != null ? medioPago.getNombre() : "Medio de pago no encontrado";
    }

    public static void mostrarVentas(VentaDAO ventaDAO, ClienteDAO clienteDAO, MedioPagoDAO medioPagoDAO) {
        System.out.println("Lista de Ventas:");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-10s %-25s %-25s\n", "ID", "Fecha", "Total", "Cliente", "Medio de Pago");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (Venta v : ventaDAO.listar()) {
            String cliente = obtenerNombreCliente(v, clienteDAO);
            String medioPago = obtenerNombreMedioPago(v, medioPagoDAO);
            System.out.printf("%-5d %-20s %-10.2f %-25s %-25s\n",
                    v.getId_venta(),
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(v.getFechaVenta()),
                    v.getTotal(),
                    cliente,
                    medioPago);
        }

        System.out.println("-----------------------------------------------------------------------------------------------------");
    }
}
