package com.utp.sistemadeventas.util;

import com.utp.sistemadeventas.dao.ClienteDAO;
import com.utp.sistemadeventas.modelos.Cliente;

/**
 *
 * @author abdieeel
 */
public class ClienteHelper {

    //VERIFICAR SI UN CLIENTE EXISTE, RETORNA UN BOOLEAN
    public static boolean existe(Cliente cRecibido, ClienteDAO clienteDAO) {
        for (Cliente cBusqueda : clienteDAO.listar()) {
            if (cBusqueda.getId_cliente().equals(cRecibido.getId_cliente())) {
                return true;
            }
        }
        return false;
    }
}

