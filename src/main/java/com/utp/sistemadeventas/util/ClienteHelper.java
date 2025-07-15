/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utp.sistemadeventas.util;

import com.utp.sistemadeventas.dao.ClienteDAO;
import com.utp.sistemadeventas.modelos.Cliente;

/**
 *
 * @author abdieeel
 */
public class ClienteHelper {

    public static boolean existe(Cliente c, ClienteDAO clienteDAO) {
        for (Cliente cl : clienteDAO.listar()) {
            if (cl.getId_cliente().equals(c.getId_cliente())) {
                return true;
            }
        }
        return false;
    }
}
