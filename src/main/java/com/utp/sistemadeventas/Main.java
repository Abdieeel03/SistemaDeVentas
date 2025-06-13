/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.utp.sistemadeventas;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.utp.sistemadeventas.vistas.VtnLogin;


/**
 *
 * @author marce
 */
public class Main {

    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        System.out.println("Hello World!");
        VtnLogin vtnLogin = new VtnLogin();
        vtnLogin.setVisible(true);
    }
}
