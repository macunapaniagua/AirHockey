/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Mario A
 */
public class Paleta extends Thread {
    // Declaracion de constantes
    private final int altoAreaJuego;    
    private final int margenDerecho;
    private final int margenIzquierdo;
    private final JLabel imagenPaleta;
    
    // Declaracion de variables
    private boolean enJuego = true;
    private int velocidadPaleta = 5;    
    private char direccionVertical = 'c';
    private char direccionHorizontal = 'c';      

    /**
     * Metodo constructor que inicializa las variables
     * @param pImagen Label con la paleta
     * @param pMargenIzq margen izquierdo hasta donde se podra mover el jugador
     * @param pMargenDer margen derecho hasta donde se podra mover el jugador
     * @param pAltura parte inferior hasta donde se puede mover el jugador
     */
    public Paleta(JLabel pImagen, int pMargenIzq, int pMargenDer, int pAltura) {
        imagenPaleta = pImagen;
        altoAreaJuego = pAltura;
        margenDerecho = pMargenDer;
        margenIzquierdo = pMargenIzq;
    }

    /**
     * Metodo utilizado para establecer la direccion horizontal de la Paleta
     *
     * @param pDireccionHorizontal Izquierda = 'i', Derecha = 'd', Centro = 'c'
     */
    public void setDireccionHorizontal(char pDireccionHorizontal) {
        direccionHorizontal = pDireccionHorizontal;
    }

    /**
     * Metodo utilizado para establecer la direccion vertical de la Paleta
     *
     * @param pDireccionVertical Arriba = 'a', Abajo = 'b', Centro = 'c'
     */
    public void setDireccionVertical(char pDireccionVertical) {
        direccionVertical = pDireccionVertical;
    }
    
    /**
     * Metodo utilizado para obtener la direccion vertical de la paleta
     * @return 'c' si no esta en movimiento, 'a' si va hacia arriba o 'b' si es hacia abajo
     */
    public char getDireccionVertical(){
        return direccionVertical;
    }
    
    /**
     * Metodo utilizado para obtener la direccion horizontal del movimiento de la paleta 
     * @return 'c' si no esta en movimiento, 'd' si va hacia la derecha o 'i' hacia la izquierda
     */
    public char getDireccionHorizontal(){
        return direccionVertical;
    }
    
    /**
     * Metodo utilizado para decirle al hilo que el juego ha terminado
     */
    public void finalizarJuego(){
        enJuego = false;
    }

    @Override
    public void run() {
        while (enJuego) {
            int posX = imagenPaleta.getX();
            int posY = imagenPaleta.getY();
            // Se verifica y se realiza el movimiento horizontal correspondiente del jugador
            if (direccionHorizontal == 'i' && posX > margenIzquierdo) {
                posX -= velocidadPaleta;
            } else if (direccionHorizontal == 'd' && (posX + imagenPaleta.getWidth()) < margenDerecho) {
                posX += velocidadPaleta;
            }
            // Se verifica y se realiza el movimiento vertical correspondiente del jugador
            if (direccionVertical == 'a' && posY > 0) {
                posY -= velocidadPaleta;
            } else if (direccionVertical == 'b' && (posY + imagenPaleta.getHeight()) < altoAreaJuego) {
                posY += velocidadPaleta;
            }            
            imagenPaleta.setLocation(posX, posY);

            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Paleta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
