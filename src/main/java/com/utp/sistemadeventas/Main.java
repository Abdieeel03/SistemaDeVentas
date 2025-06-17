/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.dao.MedioPagoDAO;
import com.utp.sistemadeventas.modelos.MedioPago;
import com.utp.sistemadeventas.vistas.VtnLogin;
import java.util.List;

/**
 *
 * @author marce
 */
public class Main {
    
    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        System.out.println("Hello World!");
        MedioPagoDAO mpDAO = new MedioPagoDAO();
        MedioPago m1 = new MedioPago();
        m1.setNombre("Efectivo");
        MedioPago m2 = new MedioPago();
        m2.setNombre("Billetera digital");
        mpDAO.agregar(m1);
        mpDAO.agregar(m2);
        List<MedioPago> lista = mpDAO.listar();
        for (MedioPago mp : lista) {
            System.out.println(mp.getIdMedioPago() + " = " + mp.getNombre());
        }
    }
}
