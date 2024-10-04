/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.primera_actividad_arq;

import Vista.Registro;  // Importa la clase Registro del paquete Vista
import javax.swing.UIManager;
/**
 *
 * @author ADMIN
 */
public class PRIMERA_ACTIVIDAD_ARQ {

    public static void main(String[] args) {
        try {
            // Establecer el tema de JTattoo (puedes cambiar el tema si lo deseas)
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");  // Tema Graphite de JTattoo
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Crear una instancia del JFrame Registro
        Registro registro = new Registro();
        
        // Mostrar el formulario
        registro.setVisible(true);
    }
}
