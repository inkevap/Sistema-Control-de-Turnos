/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacontrolturnos;

import javax.swing.SwingUtilities;
import sistemacontrolturnos.presentacion.LoginView;
import sistemacontrolturnos.util.RegistroErrores;

/**
 *
 * @author Nitro
 */
public class SistemaControlTurnos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Cualquier excepcion que no se haya capturado en ninguna capa termina aqui
        // y queda registrada en data/log.txt (incluye las del hilo de eventos de Swing).
        Thread.setDefaultUncaughtExceptionHandler((hilo, error) ->
                RegistroErrores.registrar("Error no controlado en el hilo '" + hilo.getName() + "'", error));

        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}
