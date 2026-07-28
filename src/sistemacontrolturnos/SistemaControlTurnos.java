/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacontrolturnos;

import javax.swing.SwingUtilities;
import sistemacontrolturnos.presentacion.LoginView;

/**
 *
 * @author Nitro
 */
public class SistemaControlTurnos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}
