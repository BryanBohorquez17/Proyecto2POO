package logica;

import javax.swing.*;

import presentacion.PTablero;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Othello extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7364385731013028899L;
	private PTablero tablero;
    private String[][] matriz = new String[8][8];
    private String jugadorActual = "B";
    public JButton[][] jBotones = new JButton[8][8];
    private JLabel lResultado;
    private JLabel lResultado2;

    
    
    public Othello () {
        super("Othello POO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 900);
        this.setLayout(new BorderLayout());

     
        matriz[3][3] = "N";
        matriz[3][4] = "B";
        matriz[4][3] = "B";
        matriz[4][4] = "N";

      
        this.lResultado = new JLabel("0", SwingConstants.LEFT);
        this.lResultado.setFont(new Font("Arial", Font.BOLD, 80));
        this.lResultado.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.lResultado.setVerticalAlignment(SwingConstants.CENTER);

        this.lResultado2 = new JLabel("0", SwingConstants.RIGHT);
        this.lResultado2.setFont(new Font("Arial", Font.BOLD, 80));
        this.lResultado2.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.lResultado2.setVerticalAlignment(SwingConstants.CENTER);


        JPanel pEtiquetas = new JPanel(new BorderLayout());
        JPanel pFichaNegra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pFichaNegra.add(new JLabel(new ImageIcon("media/ficha negra.png")));
        pFichaNegra.add(lResultado);
        
        JPanel pFichaBlanca = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pFichaBlanca.add(new JLabel(new ImageIcon("media/ficha blanca.png")));
        pFichaBlanca.add(lResultado2);
        
        pEtiquetas.add(pFichaNegra, BorderLayout.CENTER);
        pEtiquetas.add(pFichaBlanca, BorderLayout.NORTH);

        this.add(pEtiquetas, BorderLayout.NORTH);
        pEtiquetas.add(lResultado, BorderLayout.WEST);
        pEtiquetas.add(lResultado2, BorderLayout.EAST);
        this.add(pEtiquetas, BorderLayout.NORTH);


        tablero = new PTablero(matriz, this);
        this.add(tablero, BorderLayout.CENTER);
       

    }

    
    public void actionPerformed(ActionEvent e) {
        JButton botonClic = (JButton) e.getSource();
        int fila = -1;
        int columna = -1;

      
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (botonClic == this.jBotones[i][j]) {
                    fila = i;
                    columna = j;
                    break;
                }
            }
        }
        
        if (fila != -1 && columna != -1) {
            if (matriz[fila][columna] == null && esMovimientoValido(fila, columna)) {
                matriz[fila][columna] = jugadorActual;
                invertirFichas(fila, columna);
                jugadorActual = (jugadorActual.equals("B")) ? "N" : "B";
                actualizarBotones();
                actualizarResultados();

        
                if (hayGanador()) {
                    mostrarMensajeGanador();
                }
            }
        }
    }
    @SuppressWarnings("unused")
	private boolean hayMovimientosValidos() {
       
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matriz[i][j] == null && esMovimientoValido(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean esMovimientoValido(int fila, int columna) {
        if (matriz[fila][columna] != null) {
            return false;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if (hayFichasOponenteEnDireccion(fila, columna, i, j)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hayFichasOponenteEnDireccion(int fila, int columna, int dirFila, int dirColumna) {
        int filaActual = fila + dirFila;
        int columnaActual = columna + dirColumna;

        while (filaActual >= 0 && filaActual < 8 && columnaActual >= 0 && columnaActual < 8) {
            if (matriz[filaActual][columnaActual] == null) {
                return false;
            }

            if (matriz[filaActual][columnaActual].equals(jugadorActual)) {
                return true;
            }

            filaActual += dirFila;
            columnaActual += dirColumna;
        }

        return false;
    }

    private void invertirFichas(int fila, int columna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if (hayFichasOponenteEnDireccion(fila, columna, i, j)) {
                    invertirFichasEnDireccion(fila, columna, i, j);
                }
            }
        }
    }

    private void invertirFichasEnDireccion(int fila, int columna, int dirFila, int dirColumna) {
        int filaActual = fila + dirFila;
        int columnaActual = columna + dirColumna;

        while (filaActual >= 0 && filaActual < 8 && columnaActual >= 0 && columnaActual < 8) {
            if (matriz[filaActual][columnaActual].equals(jugadorActual)) {
                return;
            }

            matriz[filaActual][columnaActual] = jugadorActual;

            filaActual += dirFila;
            columnaActual += dirColumna;
        }
    }

    private void actualizarBotones() {
        ImageIcon iconoBlanco = new ImageIcon("media/ficha blanca.png");
        ImageIcon iconoNegro = new ImageIcon("media/ficha negra.png");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matriz[i][j] != null && matriz[i][j].equals("B")) {
                    this.jBotones[i][j].setIcon(iconoNegro);
                } else if (matriz[i][j] != null && matriz[i][j].equals("N")) {
                    this.jBotones[i][j].setIcon(iconoBlanco);
                } else {
                    this.jBotones[i][j].setIcon(null);
                }
            }
        }
    }

    private void actualizarResultados() {
        int fichasNegras = 0;
        int fichasBlancas = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matriz[i][j] != null) {
                    if (matriz[i][j].equals("B")) {
                        fichasNegras++;
                    } else if (matriz[i][j].equals("N")) {
                        fichasBlancas++;
                    }
                }
            }
        }

        this.lResultado.setText(Integer.toString(fichasNegras));
        this.lResultado2.setText(Integer.toString(fichasBlancas));
    }

    private boolean hayGanador() {
        int fichasNegras = 0;
        int fichasBlancas = 0;
        int fichasVacias = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matriz[i][j] != null) {
                    if (matriz[i][j].equals("B")) {
                        fichasNegras++;
                    } else if (matriz[i][j].equals("N")) {
                        fichasBlancas++;
                    }
                } else {
                    fichasVacias++;
                }
            }
        }

     
        if (fichasVacias == 0) {
          
            if (fichasNegras > fichasBlancas) {
                mostrarMensajeGanador("¡Jugador Negro ha ganado!");
            } else if (fichasBlancas > fichasNegras) {
                mostrarMensajeGanador("¡Jugador Blanco ha ganado!");
            } else {
                mostrarMensajeGanador("¡Empate!");
            }

          
            return true;  
        }

      
        return fichasNegras == 0 || fichasBlancas == 0;
    }


    @SuppressWarnings("unused")
	private void mostrarMensajeGanador(String mensaje) {
        int fichasNegras = Integer.parseInt(lResultado.getText());
        int fichasBlancas = Integer.parseInt(lResultado2.getText());

        JOptionPane.showMessageDialog(this, mensaje, "Fin del juego", JOptionPane.INFORMATION_MESSAGE);

    
    }
    private void mostrarMensajeGanador() {
        int fichasNegras = Integer.parseInt(lResultado.getText());
        int fichasBlancas = Integer.parseInt(lResultado2.getText());

        if (fichasNegras > fichasBlancas) {
            JOptionPane.showMessageDialog(this, "¡Jugador Negro Gana!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        } else if (fichasBlancas > fichasNegras) {
            JOptionPane.showMessageDialog(this, "¡Jugador Blanco Gana!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "¡Empate!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        }

   
    }

 

    public static void main(String[] args) {
    	Othello othello = new Othello();
        othello.setVisible(true);
    }
}

