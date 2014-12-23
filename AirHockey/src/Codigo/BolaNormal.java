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
public class BolaNormal extends Thread {

    // Declaracion de variables
    protected boolean enJuego = true;
    protected final JLabel imagenBola;
    protected final int altoAreaJuego;
    protected final int anchoAreaJuego;
    protected boolean direccionNorteSur;
    protected boolean direccionOesteEste;

    protected int velocidadHorizontal;
    protected int velocidadVertical;

    /**
     * Metodo constructor
     * @param pImagen Label con la imagen de la bola
     * @param pAltoCancha Altura del terreno de juego
     * @param pAnchoCancha  Ancho del terreno de juego
     */
    public BolaNormal(JLabel pImagen, int pAltoCancha, int pAnchoCancha) {
        imagenBola = pImagen;
        altoAreaJuego = pAltoCancha;
        anchoAreaJuego = pAnchoCancha;
        // Se generan las direcciones de las bolas de manera aleatoria
        Random rand = new Random();
        direccionOesteEste = rand.nextBoolean();
        direccionNorteSur = rand.nextBoolean();
    }
    
    /**
     * Metodo utilizado para finalizar el hilo de la bola
     */
    public void finalizarJuego(){
        enJuego = false;
    }

    @Override
    public void run() {
        while (enJuego) {
            int posX = imagenBola.getX();
            int posY = imagenBola.getY();
            // Se calcula el nuevo movimiento horizontal de la bola
            if (direccionOesteEste) {
                if(posX + imagenBola.getWidth() < anchoAreaJuego){
                    posX++;
                }else{
                    posX--;
                    direccionOesteEste = false;
                }
            } else {
                if(posX > 0){
                    posX--;
                }else{
                    posX++;
                    direccionOesteEste = true;
                }
            }
            // Se calcula el nuevo movimiento vertical de la bola
            if (direccionNorteSur) {
                if(posY + imagenBola.getHeight() < altoAreaJuego){
                    posY++;
                }else{
                    posY--;
                    direccionNorteSur = false;
                }
            } else {
                if(posY > 0){
                    posY--;
                }else{
                    posY++;
                    direccionNorteSur = true;
                }
            }
            // Establece la nueva coordenada de la bola
            imagenBola.setLocation(posX, posY);
            try {
                sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(BolaNormal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
