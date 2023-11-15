package presentacion;

import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Fprincipal extends JFrame implements ActionListener {
    private JButton jBotones[][] = new JButton[8][8];
    private String matriz[][] = new String[8][8];
    private String jugadorActual = "B";
    private JLabel lResultado;
    private JLabel lResultado2;

    public Fprincipal() {
        super("Othello POO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLayout(new BorderLayout());

        // Panel para los botones
        JPanel pBotones = new JPanel();
        pBotones.setLayout(new GridLayout(8, 8));
        this.add(pBotones, BorderLayout.CENTER);

        // Inicializar botones con color de ficha negra
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.matriz[i][j] = "";
                this.jBotones[i][j] = new JButton();
                pBotones.add(this.jBotones[i][j]);
                this.jBotones[i][j].setBackground(Color.GREEN);
                this.jBotones[i][j].addActionListener(this);
            }
        }

  
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
        pEtiquetas.add(lResultado, BorderLayout.WEST);
        pEtiquetas.add(lResultado2, BorderLayout.EAST);
        this.add(pEtiquetas, BorderLayout.NORTH);

    
        actualizarBotones();
        actualizarResultados();
        actualizarPosiblesJugadas();
    }

    @Override
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
          
            if (matriz[fila][columna].isEmpty() && esMovimientoValido(fila, columna)) {
       
                matriz[fila][columna] = jugadorActual;

 
                invertirFichas(fila, columna);

        
                jugadorActual = (jugadorActual.equals("B")) ? "N" : "B";

                             actualizarBotones();
                actualizarResultados();
                actualizarPosiblesJugadas();
            }
        }
    }

    private boolean esMovimientoValido(int fila, int columna) {

        if (!matriz[fila][columna].isEmpty()) {
            return false;
        }


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
               
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
            if (matriz[filaActual][columnaActual].equals(jugadorActual)) {
           
                return true;
            }

            if (matriz[filaActual][columnaActual].isEmpty()) {
      
                return false;
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

    private void actualizarPosiblesJugadas() {
    	
    }

    private void actualizarBotones() {
        ImageIcon iconoBlanco = new ImageIcon("media/ficha blanca.jpg");
        ImageIcon iconoNegro = new ImageIcon("media/ficha negra.png");

 
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matriz[i][j].equals("B")) {
                    this.jBotones[i][j].setIcon(iconoNegro);
                } else if (matriz[i][j].equals("N")) {
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
                if (matriz[i][j].equals("B")) {
                    fichasNegras++;
                } else if (matriz[i][j].equals("N")) {
                    fichasBlancas++;
                }
            }
        }


        this.lResultado.setText(Integer.toString(fichasNegras));
        this.lResultado2.setText(Integer.toString(fichasBlancas));
    }

    public static void main(String[] args) {
        Fprincipal fprincipal = new Fprincipal();
        fprincipal.setVisible(true);
    }
}