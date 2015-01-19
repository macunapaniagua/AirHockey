/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Mario A
 */
public class Temporizador extends Thread {

    // Declaracion de variables
    private boolean enJuego = true;
    private int minutos;
    private int segundos;
    private final ArrayList<JLabel> imagenesTiempo;

    // Componentes que se van a detener al terminarse el tiempo de juego
    private ArrayList<Paleta> jugadores;
    private ArrayList<Teleport> teleports;
    private ArrayList<Bloque> bloques;
    private ArrayList<BolaNormal> bolas;

    /**
     * Metodo constructor
     *
     * @param pMin Minutos que dura el juego
     * @param pSeg Segundos que dura el juego
     * @param pMin1 Imagen de las decenas de minutos
     * @param pMin2 Imagen de las unidades de minuto
     * @param pSeg1 Imagen de las decenas de segundos
     * @param pSeg2 Imagen de las unidades de segundos
     */
    public Temporizador(int pMin, int pSeg, JLabel pMin1, JLabel pMin2, JLabel pSeg1, JLabel pSeg2) {
        // Se genera el arreglo con las imagenes del tiempo
        imagenesTiempo = new ArrayList<>();
        imagenesTiempo.add(pMin1);
        imagenesTiempo.add(pMin2);
        imagenesTiempo.add(pSeg1);
        imagenesTiempo.add(pSeg2);
        // Se establece el tiempo
        minutos = pMin;
        segundos = pSeg;
        cambiarTiempoVisual();
    }

    /**
     * Se agregan los componentes de juego, los cuales se desea finalizar su
     * ejecucion una vez completado el tiempo
     *
     * @param pJugadores jugadores del juego
     * @param pBloques bloques del juego que se mueven verticalmente
     * @param pTeleports teleports que se mueven alrededor del campo de juego
     * @param pBolas bolas del juego
     */
    public void agregarHilosDeJuego(ArrayList pJugadores, ArrayList pBloques, ArrayList pTeleports, ArrayList pBolas) {
        bloques = pBloques;
        jugadores = pJugadores;
        teleports = pTeleports;
        bolas = pBolas;
    }

    /**
     * Metodo para cambiar las imagenes del tiempo de juego
     *
     * @param pTiempo String con el tiempo de juego que se va a establecer
     */
    private void cambiarTiempoVisual() {
        // Se forma un string con el valor del tiempo de la foma MMss
        String tiempo = "";
        // Se agregan los minutos
        if (minutos < 10) {
            tiempo += "0" + minutos;
        } else {
            tiempo += minutos;
        }
        // Se agregan los segundos
        if (segundos < 10) {
            tiempo += "0" + segundos;
        } else {
            tiempo += segundos;
        }
        for (int i = 0; i < tiempo.length(); i++) {
            String imagen = "/Imagenes/" + tiempo.charAt(i) + ".png";
            imagenesTiempo.get(i).setIcon(new ImageIcon(getClass().getResource(imagen)));
        }
    }

    @Override
    public void run() {
        while (enJuego) {
            // Se detiene un segundo mientras se muestra el tiempo
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Se calcula el nuevo tiempo
            if (segundos > 0) {
                segundos--;
            } else {
                segundos = 59;
                minutos--;
            }
            cambiarTiempoVisual();

            // Verifica si ya se debe terminar el juego
            if (minutos == 0 && segundos == 0) {
                enJuego = false;
            }
        }
        // Se detienen todos los componentes luego de que el tiempo finaliza
        for (Paleta jugador : jugadores) {
            jugador.finalizarJuego();
        }
        for (Bloque bloque : bloques) {
            bloque.finalizarJuego();
        }
        for (Teleport teleport : teleports) {
            teleport.finalizarJuego();
        }
        for(BolaNormal bola : bolas){
            bola.finalizarJuego();
        }
        JOptionPane.showMessageDialog(null, "Juego Finalizado");
    }
}
