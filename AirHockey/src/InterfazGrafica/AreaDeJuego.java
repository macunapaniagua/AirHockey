/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazGrafica;

import Codigo.Bloque;
import Codigo.BolaFuego;
import Codigo.BolaNormal;
import Codigo.BolaRandom;
import Codigo.Constantes;
import Codigo.Paleta;
import Codigo.Teleport;
import Codigo.Temporizador;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mario A
 */
public class AreaDeJuego extends javax.swing.JFrame implements Constantes {

    // Declaracion de constantes
    private final int ANCHO_AREA_ACCION;
    private final int ALTO_AREA_JUEGO;
    private final int ANCHO_AREA_JUEGO;
    private final int ALTO_AREA_TELEPORTS;
    private final int ANCHO_AREA_TELEPORTS;

    // Declaracion de variables
    private BolaFuego bolaDeFuego;
    private BolaNormal bolaNormal;
    private BolaRandom bolaAleatoria;
    private Temporizador temporizador;

    private ArrayList<Paleta> jugadores;
    private ArrayList<Bloque> bloques;
    private ArrayList<Teleport> teleports;
    private ArrayList<BolaNormal> bolas;

    /**
     * Creates new form AreaDeJuego
     */
    public AreaDeJuego() {
        initComponents();
        // Se inicializa el valor de las constantes
        ANCHO_AREA_ACCION = 100;
        ANCHO_AREA_JUEGO = Pnl_CampoDeJuego.getWidth();
        ALTO_AREA_JUEGO = Pnl_CampoDeJuego.getHeight();
        ANCHO_AREA_TELEPORTS = Pnl_Teleports.getWidth();
        ALTO_AREA_TELEPORTS = Pnl_Teleports.getHeight();

        ubicarComponentes();
        crearEInicializarHilos();
    }

    /**
     * Metodo utilizado para ubicar los componentes en la pantalla
     */
    private void ubicarComponentes() {
        // Ubica los bloques
        int posX = (ANCHO_AREA_JUEGO / 3) - (Lbl_BloqueIzq.getWidth() / 2);
        Lbl_BloqueIzq.setLocation(posX, Lbl_BloqueIzq.getY());

        posX = (2 * ANCHO_AREA_JUEGO / 3) - (Lbl_BloqueDer.getWidth() / 2);
        Lbl_BloqueDer.setLocation(posX, Lbl_BloqueDer.getY());

        // Se ubica las paletas de los jugadores
        int posY = (ALTO_AREA_JUEGO / 2) - (Lbl_Player1.getHeight() / 2);
        posX = (ANCHO_AREA_ACCION / 2) - (Lbl_Player1.getWidth() / 2);
        Lbl_Player1.setLocation(posX, posY);

        posY = (ALTO_AREA_JUEGO / 2) - (Lbl_Player2.getHeight() / 2);
        posX = (ANCHO_AREA_JUEGO - (ANCHO_AREA_ACCION / 2)) - (Lbl_Player2.getWidth() / 2);
        Lbl_Player2.setLocation(posX, posY);
    }

    /**
     * Metodo utilizado para crear e iniciar cada uno de los Threads
     */
    private void crearEInicializarHilos() {
        // Se crea el arraylist y los jugadores del juego
        jugadores = new ArrayList<>();
        jugadores.add(new Paleta(Lbl_Player1, 0, ANCHO_AREA_ACCION, ALTO_AREA_JUEGO));
        jugadores.add(new Paleta(Lbl_Player2, ANCHO_AREA_JUEGO - ANCHO_AREA_ACCION, ANCHO_AREA_JUEGO, ALTO_AREA_JUEGO));

        // Se crea el arrayList y los teleports que este va a contener
        bloques = new ArrayList<>();
        bloques.add(new Bloque(Lbl_BloqueIzq, ALTO_AREA_JUEGO));
        bloques.add(new Bloque(Lbl_BloqueDer, ALTO_AREA_JUEGO));

        // Se crea el ArrayList y todos los teleports que va a contener 
        teleports = new ArrayList<>();
        teleports.add(new Teleport(Lbl_Teleport1, rigthRedPipe, ANCHO_AREA_TELEPORTS, ALTO_AREA_TELEPORTS, 'd'));
        teleports.add(new Teleport(Lbl_Teleport2, leftBluePipe, ANCHO_AREA_TELEPORTS, ALTO_AREA_TELEPORTS, 'u'));
        teleports.add(new Teleport(Lbl_Teleport3, topRedPipe, ANCHO_AREA_TELEPORTS, ALTO_AREA_TELEPORTS, 'r'));
        teleports.add(new Teleport(Lbl_Teleport4, topYellowPipe, ANCHO_AREA_TELEPORTS, ALTO_AREA_TELEPORTS, 'r'));
        teleports.add(new Teleport(Lbl_Teleport5, bottomGreenPipe, ANCHO_AREA_TELEPORTS, ALTO_AREA_TELEPORTS, 'l'));
        teleports.add(new Teleport(Lbl_Teleport6, bottomYellowPipe, ANCHO_AREA_TELEPORTS, ALTO_AREA_TELEPORTS, 'l'));

        // Se crea el ArrayList y todas las bolas que van a participar en el juego
        bolas = new ArrayList<>();

        bolaDeFuego = new BolaFuego(Lbl_BolaFuego, ALTO_AREA_JUEGO, ANCHO_AREA_JUEGO);
        bolaNormal = new BolaNormal(Lbl_BolaNormal, ALTO_AREA_JUEGO, ANCHO_AREA_JUEGO);
        bolaAleatoria = new BolaRandom(Lbl_BolaRandom, ALTO_AREA_JUEGO, ANCHO_AREA_JUEGO);

        bolas.add(bolaNormal);
        bolas.add(bolaDeFuego);
        bolas.add(bolaAleatoria);

        // Se crea el temporizador que contendra el tiempo de juego y controlara la finalizacion de los hilos
        temporizador = new Temporizador(0, 15, Lbl_Min1, Lbl_Min2, Lbl_Seg1, Lbl_Seg2);
        temporizador.agregarHilosDeJuego(jugadores, bloques, teleports, bolas);

        // Se inicia la ejecucion del hilo de ambos jugadores
        for (Paleta jugador : jugadores) {
            jugador.start();
        }

        // Se inicia el hilo de los bloques
        for (Bloque bloque : bloques) {
            bloque.start();
        }

        // Se inicia el hilo de los teleports
        for (Teleport teleport : teleports) {
            teleport.start();
        }

        bolaNormal.start();
        bolaDeFuego.start();
        bolaAleatoria.start();

        temporizador.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pnl_Marcadores = new javax.swing.JPanel();
        Lbl_Min1 = new javax.swing.JLabel();
        Lbl_Min2 = new javax.swing.JLabel();
        Lbl_DosPuntos = new javax.swing.JLabel();
        Lbl_Seg1 = new javax.swing.JLabel();
        Lbl_Seg2 = new javax.swing.JLabel();
        Pnl_Teleports = new javax.swing.JLayeredPane();
        Pnl_CampoDeJuego = new javax.swing.JPanel();
        Lbl_Player1 = new javax.swing.JLabel();
        Lbl_Player2 = new javax.swing.JLabel();
        Lbl_BolaFuego = new javax.swing.JLabel();
        Lbl_BolaNormal = new javax.swing.JLabel();
        Lbl_BolaRandom = new javax.swing.JLabel();
        Lbl_BloqueIzq = new javax.swing.JLabel();
        Lbl_BloqueDer = new javax.swing.JLabel();
        Lbl_Sorpresa1 = new javax.swing.JLabel();
        Lbl_Sorpresa2 = new javax.swing.JLabel();
        Lbl_Sorpresa3 = new javax.swing.JLabel();
        Lbl_Sorpresa4 = new javax.swing.JLabel();
        Lbl_Sorpresa5 = new javax.swing.JLabel();
        Lbl_Sorpresa6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Lbl_Teleport3 = new javax.swing.JLabel();
        Lbl_Teleport4 = new javax.swing.JLabel();
        Lbl_Teleport5 = new javax.swing.JLabel();
        Lbl_Teleport6 = new javax.swing.JLabel();
        Lbl_Teleport2 = new javax.swing.JLabel();
        Lbl_Teleport1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AirHockey");
        setMinimumSize(new java.awt.Dimension(1020, 600));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AreaDeJuego.this.keyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                AreaDeJuego.this.keyReleased(evt);
            }
        });

        Pnl_Marcadores.setBackground(new java.awt.Color(153, 204, 255));
        Pnl_Marcadores.setPreferredSize(new java.awt.Dimension(1247, 100));
        Pnl_Marcadores.setLayout(null);

        Lbl_Min1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/0.png"))); // NOI18N
        Pnl_Marcadores.add(Lbl_Min1);
        Lbl_Min1.setBounds(550, 42, 28, 47);

        Lbl_Min2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/0.png"))); // NOI18N
        Pnl_Marcadores.add(Lbl_Min2);
        Lbl_Min2.setBounds(584, 42, 28, 47);

        Lbl_DosPuntos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Puntos.png"))); // NOI18N
        Pnl_Marcadores.add(Lbl_DosPuntos);
        Lbl_DosPuntos.setBounds(618, 42, 12, 47);

        Lbl_Seg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/0.png"))); // NOI18N
        Pnl_Marcadores.add(Lbl_Seg1);
        Lbl_Seg1.setBounds(636, 42, 28, 47);

        Lbl_Seg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/0.png"))); // NOI18N
        Pnl_Marcadores.add(Lbl_Seg2);
        Lbl_Seg2.setBounds(670, 42, 28, 47);

        Pnl_Teleports.setBackground(new java.awt.Color(0, 102, 102));
        Pnl_Teleports.setOpaque(true);

        Pnl_CampoDeJuego.setBackground(new java.awt.Color(51, 51, 51));
        Pnl_CampoDeJuego.setMaximumSize(new java.awt.Dimension(1020, 600));
        Pnl_CampoDeJuego.setMinimumSize(new java.awt.Dimension(1020, 600));
        Pnl_CampoDeJuego.setPreferredSize(new java.awt.Dimension(1203, 530));
        Pnl_CampoDeJuego.setLayout(null);

        Lbl_Player1.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Player1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PaletaIzquierda.png"))); // NOI18N
        Lbl_Player1.setFocusable(false);
        Lbl_Player1.setRequestFocusEnabled(false);
        Pnl_CampoDeJuego.add(Lbl_Player1);
        Lbl_Player1.setBounds(18, 125, 16, 130);

        Lbl_Player2.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Player2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/PaleteDerecha.png"))); // NOI18N
        Lbl_Player2.setFocusable(false);
        Lbl_Player2.setRequestFocusEnabled(false);
        Pnl_CampoDeJuego.add(Lbl_Player2);
        Lbl_Player2.setBounds(1150, 190, 16, 130);

        Lbl_BolaFuego.setBackground(new java.awt.Color(255, 255, 0));
        Lbl_BolaFuego.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BolaFuego.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_BolaFuego);
        Lbl_BolaFuego.setBounds(220, 279, 50, 50);

        Lbl_BolaNormal.setBackground(new java.awt.Color(255, 255, 0));
        Lbl_BolaNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BolaNormal.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_BolaNormal);
        Lbl_BolaNormal.setBounds(637, 70, 50, 50);

        Lbl_BolaRandom.setBackground(new java.awt.Color(255, 255, 0));
        Lbl_BolaRandom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BolaRandom.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_BolaRandom);
        Lbl_BolaRandom.setBounds(873, 317, 50, 50);

        Lbl_BloqueIzq.setBackground(new java.awt.Color(0, 102, 0));
        Lbl_BloqueIzq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lbl_BloqueIzq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bloque.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_BloqueIzq);
        Lbl_BloqueIzq.setBounds(237, 150, 71, 80);

        Lbl_BloqueDer.setBackground(new java.awt.Color(0, 102, 0));
        Lbl_BloqueDer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Lbl_BloqueDer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bloque.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_BloqueDer);
        Lbl_BloqueDer.setBounds(320, 350, 71, 80);

        Lbl_Sorpresa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sorpresa.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_Sorpresa1);
        Lbl_Sorpresa1.setBounds(363, 182, 50, 48);

        Lbl_Sorpresa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sorpresa.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_Sorpresa2);
        Lbl_Sorpresa2.setBounds(777, 348, 50, 48);

        Lbl_Sorpresa3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sorpresa.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_Sorpresa3);
        Lbl_Sorpresa3.setBounds(735, 330, 50, 48);

        Lbl_Sorpresa4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sorpresa.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_Sorpresa4);
        Lbl_Sorpresa4.setBounds(816, 243, 50, 48);

        Lbl_Sorpresa5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sorpresa.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_Sorpresa5);
        Lbl_Sorpresa5.setBounds(514, 335, 50, 48);

        Lbl_Sorpresa6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sorpresa.png"))); // NOI18N
        Pnl_CampoDeJuego.add(Lbl_Sorpresa6);
        Lbl_Sorpresa6.setBounds(625, 182, 50, 48);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo3.png"))); // NOI18N
        Pnl_CampoDeJuego.add(jLabel1);
        jLabel1.setBounds(0, 0, 1203, 530);

        Pnl_Teleports.add(Pnl_CampoDeJuego);
        Pnl_CampoDeJuego.setBounds(22, 22, 1203, 530);

        Lbl_Teleport3.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Teleport3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pipes/TopPipeRed.png"))); // NOI18N
        Pnl_Teleports.add(Lbl_Teleport3);
        Lbl_Teleport3.setBounds(230, 0, 65, 22);

        Lbl_Teleport4.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Teleport4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pipes/TopPipeYellow.png"))); // NOI18N
        Pnl_Teleports.add(Lbl_Teleport4);
        Lbl_Teleport4.setBounds(820, 0, 65, 22);

        Lbl_Teleport5.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Teleport5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pipes/BottomPipeGreen.png"))); // NOI18N
        Pnl_Teleports.add(Lbl_Teleport5);
        Lbl_Teleport5.setBounds(270, 552, 65, 22);

        Lbl_Teleport6.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Teleport6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pipes/BottomPipeYellow.png"))); // NOI18N
        Pnl_Teleports.add(Lbl_Teleport6);
        Lbl_Teleport6.setBounds(820, 552, 65, 22);

        Lbl_Teleport2.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Teleport2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pipes/LeftPipeBlue.png"))); // NOI18N
        Pnl_Teleports.add(Lbl_Teleport2);
        Lbl_Teleport2.setBounds(0, 300, 22, 65);

        Lbl_Teleport1.setBackground(new java.awt.Color(153, 0, 0));
        Lbl_Teleport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pipes/RightPipeRed.png"))); // NOI18N
        Pnl_Teleports.add(Lbl_Teleport1);
        Lbl_Teleport1.setBounds(1225, 220, 22, 65);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Pnl_Marcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pnl_Teleports, javax.swing.GroupLayout.PREFERRED_SIZE, 1247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Pnl_Marcadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Pnl_Teleports, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Evento utilizado para cambiar la direccion de la paleta del jugador de
     * acuerdo a la tecla que acaba de ser presionada
     *
     * @param evt
     */
    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        // Obtiene la tecla presionada
        int key = evt.getKeyCode();
        // Cambia el valor a true de la tecla presionada
        if (key == KeyEvent.VK_UP) {
            jugadores.get(1).setDireccionVertical('a');
        } else if (key == KeyEvent.VK_DOWN) {
            jugadores.get(1).setDireccionVertical('b');
        } else if (key == KeyEvent.VK_LEFT) {
            jugadores.get(1).setDireccionHorizontal('i');
        } else if (key == KeyEvent.VK_RIGHT) {
            jugadores.get(1).setDireccionHorizontal('d');
        } else if (key == KeyEvent.VK_W) {
            jugadores.get(0).setDireccionVertical('a');
        } else if (key == KeyEvent.VK_S) {
            jugadores.get(0).setDireccionVertical('b');
        } else if (key == KeyEvent.VK_A) {
            jugadores.get(0).setDireccionHorizontal('i');
        } else if (key == KeyEvent.VK_D) {
            jugadores.get(0).setDireccionHorizontal('d');
        }
    }//GEN-LAST:event_keyPressed

    /**
     * Evento utilizado para cambiar la direccion de la paleta del jugador de
     * acuerdo a la tecla que acaba de soltarse
     *
     * @param evt
     */
    private void keyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyReleased
        // Obtiene la tecla presionada
        int key = evt.getKeyCode();
        // Cambia el valor a true de la tecla presionada
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            jugadores.get(1).setDireccionVertical('c');
        } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            jugadores.get(1).setDireccionHorizontal('c');
        } else if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) {
            jugadores.get(0).setDireccionVertical('c');
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            jugadores.get(0).setDireccionHorizontal('c');
        }
    }//GEN-LAST:event_keyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AreaDeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AreaDeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AreaDeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AreaDeJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AreaDeJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Lbl_BloqueDer;
    private javax.swing.JLabel Lbl_BloqueIzq;
    private javax.swing.JLabel Lbl_BolaFuego;
    private javax.swing.JLabel Lbl_BolaNormal;
    private javax.swing.JLabel Lbl_BolaRandom;
    private javax.swing.JLabel Lbl_DosPuntos;
    private javax.swing.JLabel Lbl_Min1;
    private javax.swing.JLabel Lbl_Min2;
    private javax.swing.JLabel Lbl_Player1;
    private javax.swing.JLabel Lbl_Player2;
    private javax.swing.JLabel Lbl_Seg1;
    private javax.swing.JLabel Lbl_Seg2;
    private javax.swing.JLabel Lbl_Sorpresa1;
    private javax.swing.JLabel Lbl_Sorpresa2;
    private javax.swing.JLabel Lbl_Sorpresa3;
    private javax.swing.JLabel Lbl_Sorpresa4;
    private javax.swing.JLabel Lbl_Sorpresa5;
    private javax.swing.JLabel Lbl_Sorpresa6;
    private javax.swing.JLabel Lbl_Teleport1;
    private javax.swing.JLabel Lbl_Teleport2;
    private javax.swing.JLabel Lbl_Teleport3;
    private javax.swing.JLabel Lbl_Teleport4;
    private javax.swing.JLabel Lbl_Teleport5;
    private javax.swing.JLabel Lbl_Teleport6;
    private javax.swing.JPanel Pnl_CampoDeJuego;
    private javax.swing.JPanel Pnl_Marcadores;
    private javax.swing.JLayeredPane Pnl_Teleports;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
