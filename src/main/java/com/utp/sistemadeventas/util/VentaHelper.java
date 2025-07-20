package com.utp.sistemadeventas.util;

/**
 *
 * @author Abdieeel
 */
import com.utp.sistemadeventas.dao.ClienteDAO;
import com.utp.sistemadeventas.dao.MedioPagoDAO;
import com.utp.sistemadeventas.modelos.Cliente;
import com.utp.sistemadeventas.modelos.MedioPago;
import com.utp.sistemadeventas.modelos.Venta;


public class VentaHelper {

    public static String obtenerNombreCliente(Venta venta, ClienteDAO clienteDAO) {
        Cliente cliente = clienteDAO.buscarPorId(venta.getIdCliente());
        return cliente != null ? cliente.getNombre() : "Cliente no encontrado";
    }

    public static String obtenerNombreMedioPago(Venta venta, MedioPagoDAO medioPagoDAO) {
        MedioPago medioPago = medioPagoDAO.buscarPorId(String.valueOf(venta.getIdMedioPago()));
        return medioPago != null ? medioPago.getNombre() : "Medio de pago no encontrado";
    }

}
