/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import InterfazGrafica.AreaDeJuego;

/**
 *
 * @author Mario A
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        AreaDeJuego gameWindow = new AreaDeJuego();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
    }

}
