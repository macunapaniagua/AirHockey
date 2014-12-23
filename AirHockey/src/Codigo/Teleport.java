/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Mario A
 */
public class Teleport extends Thread implements Constantes {

    // Declaracion de constantes
    private final int panelWidth;
    private final int panelHeight;
    private final JLabel teleport;

    // Declaracion de Variables   
    private char direccion;    
    private String imagenActual;
    private int velocidadTeleport;
    private boolean enJuego = true;

    /**
     * Metodo constructor
     *
     * @param pTeleport Label que contiene la imagen del teleport
     * @param pImagen direccion de la imagen con el color respectivo que es
     * usada en el teleport
     * @param pAnchoTablero ancho del tablero por donde se movera el teleport
     * @param pAltoTablero alto del tablero por donde se mueve el teleport
     * @param pDireccion direccion de movimiento
     */
    public Teleport(JLabel pTeleport, String pImagen, int pAnchoTablero, int pAltoTablero, char pDireccion) {
        teleport = pTeleport;
        imagenActual = pImagen;
        direccion = pDireccion;
        panelWidth = pAnchoTablero;
        panelHeight = pAltoTablero;
    }

    /**
     * Establece la imagen izquierda del teleport cuando este cambia de posicion
     */
    private void setLeftImage() {
        if (imagenActual.equals(bottomGreenPipe)) {
            imagenActual = leftGreenPipe;
        } else if (imagenActual.equals(bottomBluePipe)) {
            imagenActual = leftBluePipe;
        } else if (imagenActual.equals(bottomRedPipe)) {
            imagenActual = leftRedPipe;
        } else {
            imagenActual = leftYellowPipe;
        }
        teleport.setIcon(new ImageIcon(this.getClass().getResource(imagenActual)));
    }

    /**
     * Establece la imagen derecha del teleport cuando este cambia de posicion
     */
    private void setRightImage() {
        if (imagenActual.equals(topBluePipe)) {
            imagenActual = rigthBluePipe;
        } else if (imagenActual.equals(topGreenPipe)) {
            imagenActual = rigthGreenPipe;
        } else if (imagenActual.equals(topRedPipe)) {
            imagenActual = rigthRedPipe;
        } else {
            imagenActual = rigthYellowPipe;
        }
        teleport.setIcon(new ImageIcon(this.getClass().getResource(imagenActual)));
    }

    /**
     * Establece la imagen de arriba del teleport cuando este cambia de posicion
     */
    private void setTopImage() {
        if (imagenActual.equals(leftBluePipe)) {
            imagenActual = topBluePipe;
        } else if (imagenActual.equals(leftGreenPipe)) {
            imagenActual = topGreenPipe;
        } else if (imagenActual.equals(leftRedPipe)) {
            imagenActual = topRedPipe;
        } else {
            imagenActual = topYellowPipe;
        }
        teleport.setIcon(new ImageIcon(this.getClass().getResource(imagenActual)));
    }

    /**
     * Establece la imagen de abajo del teleport cuando este cambia de posicion
     */
    private void setButtomImage() {
        if (imagenActual.equals(rigthBluePipe)) {
            imagenActual = bottomBluePipe;
        } else if (imagenActual.equals(rigthGreenPipe)) {
            imagenActual = bottomGreenPipe;
        } else if (imagenActual.equals(rigthRedPipe)) {
            imagenActual = bottomRedPipe;
        } else {
            imagenActual = bottomYellowPipe;
        }
        teleport.setIcon(new ImageIcon(this.getClass().getResource(imagenActual)));
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
            try {
                int posX = teleport.getX();
                int posY = teleport.getY();
                // Comprueba la direccion del teleport y lo mueve hacia la sig posicion
                if (direccion == 'r') {
                    if (posX + teleport.getWidth() < panelWidth) {
                        posX++;
                    } else {
                        posX = panelWidth - teleport.getHeight();
                        teleport.setSize(teleport.getHeight(), teleport.getWidth());
                        setRightImage();
                        direccion = 'd';
                    }
                } else if (direccion == 'l') {
                    if (posX > 0) {
                        posX--;
                    } else {
                        teleport.setSize(teleport.getHeight(), teleport.getWidth());
                        setLeftImage();
                        direccion = 'u';
                    }
                } else if (direccion == 'd') {
                    if (posY + teleport.getHeight() < panelHeight) {
                        posY++;
                    } else {
                        posY = panelHeight - teleport.getWidth();
                        teleport.setSize(teleport.getHeight(), teleport.getWidth());
                        setButtomImage();
                        direccion = 'l';
                    }
                } else {
                    if (posY > 0) {
                        posY--;
                    } else {
                        teleport.setSize(teleport.getHeight(), teleport.getWidth());
                        setTopImage();
                        direccion = 'r';
                    }
                }
                // Mueve la imagen a la nueva posicion
                teleport.setLocation(posX, posY);
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Teleport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
