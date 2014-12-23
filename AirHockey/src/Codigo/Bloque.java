/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Mario A
 */
public class Bloque extends Thread {

    // Declaracion de variables
    private int velocidad;
    private boolean enJuego = true;
    private final int alturaAreaJuego;
    private final JLabel imagenBloque;
    private boolean direccionNorteSur;

    /**
     * Metodo constructor que inicializa las variables
     *
     * @param pImagen imagen del bloque
     * @param pAltura altura del panel donde se mueven los bloques
     */
    public Bloque(JLabel pImagen, int pAltura) {
        alturaAreaJuego = pAltura;
        imagenBloque = pImagen;
        direccionNorteSur = new Random().nextBoolean();
    }

    /**
     * Metodo utiliado para camiar la velocidad de movimiento del bloque
     *
     * @param pNuevaVelocidad
     */
    public void setVelocidad(int pNuevaVelocidad) {
        velocidad = pNuevaVelocidad;
    }
    
    /**
     * Metodo utilizado para decirle al hilo que el juego ha terminado
     */
    public void finalizarJuego(){
        enJuego = false;
    }

    /**
     * Metodo utilizado para iniciar el moviemiento de los bloques
     */
    @Override
    public void run() {
        while (enJuego) {
            int posX = imagenBloque.getX();
            int posY = imagenBloque.getY();
            // Verifica la direccion del bloque y lo mueve
            if (direccionNorteSur) {
                if ((posY + imagenBloque.getHeight()) < alturaAreaJuego) {
                    posY += 1;
                } else {
                    posY -= 1;
                    direccionNorteSur = false;
                }
            } else {                
                if (posY > 0) {
                    posY -= 1;
                } else {
                    posY += 1;
                    direccionNorteSur = true;
                }
            }
            // Mueve el bloque a la nueva coordenada
            imagenBloque.setLocation(posX, posY);

            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Bloque.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
