package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fprincipal extends JFrame implements ActionListener {
    private Tablero tablero;
    private String[][] matriz = new String[8][8];
    private String jugadorActual = "B";
    JButton[][] jBotones = new JButton[8][8];
    private JLabel lResultado;
    private JLabel lResultado2;

    public Fprincipal() {
        super("Othello POO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
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
        pEtiquetas.add(lResultado, BorderLayout.WEST);
        pEtiquetas.add(lResultado2, BorderLayout.EAST);
        this.add(pEtiquetas, BorderLayout.NORTH);


        tablero = new Tablero(matriz, this);
        this.add(tablero, BorderLayout.CENTER);

     
        actualizarBotones();
        actualizarResultados();
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

            reiniciarJuego();
            return true;  
        }

      
        return fichasNegras == 0 || fichasBlancas == 0;
    }


    private void mostrarMensajeGanador(String mensaje) {
        int fichasNegras = Integer.parseInt(lResultado.getText());
        int fichasBlancas = Integer.parseInt(lResultado2.getText());

        JOptionPane.showMessageDialog(this, mensaje, "Fin del juego", JOptionPane.INFORMATION_MESSAGE);

        reiniciarJuego();
    }
    private void mostrarMensajeGanador() {
        int fichasNegras = Integer.parseInt(lResultado.getText());
        int fichasBlancas = Integer.parseInt(lResultado2.getText());

        if (fichasNegras > fichasBlancas) {
            JOptionPane.showMessageDialog(this, "¡Jugador Negro ha ganado!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        } else if (fichasBlancas > fichasNegras) {
            JOptionPane.showMessageDialog(this, "¡Jugador Blanco ha ganado!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "¡Empate!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        }

        reiniciarJuego();
    }

    private void reiniciarJuego() {
       
        jugadorActual = "B";
        tablero.inicializarTablero(matriz);
        tablero.actualizarBotones();
        actualizarResultados();
    }

    public static void main(String[] args) {
        Fprincipal fprincipal = new Fprincipal();
        fprincipal.setVisible(true);
    }
}

class Tablero extends JPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String[][] matriz;
    private Fprincipal fprincipal;

    public Tablero(String[][] matriz, Fprincipal fprincipal) {
        this.matriz = matriz;
        this.fprincipal = fprincipal;
        this.setLayout(new GridLayout(8, 8));
        this.setBackground(Color.GREEN);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.addActionListener(fprincipal);
                this.fprincipal.jBotones[i][j] = button;
                this.add(button);

                if ((i == 3 || i == 4) && (j == 3 || j == 4)) {
                    ImageIcon icon;
                    if (i == 3 && j == 3) {
                        icon = new ImageIcon("media/ficha negra.png");
                    } else if (i == 3 && j == 4) {
                        icon = new ImageIcon("media/ficha blanca.png");
                    } else if (i == 4 && j == 3) {
                        icon = new ImageIcon("media/ficha blanca.png");
                    } else {
                        icon = new ImageIcon("media/ficha negra.png");
                    }
                    button.setIcon(icon);
                }
            }
        }
    }

    public void actualizarBotones() {
		// TODO Auto-generated method stub
		
	}

	public void inicializarTablero(String[][] matriz2) {
		// TODO Auto-generated method stub
		
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int cellWidth = width / 8;
        int cellHeight = height / 8;

        g.setColor(Color.BLACK);

        for (int i = 1; i < 8; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }

        for (int i = 1; i < 8; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
        }
    }
}