package presentacion;

import javax.swing.Icon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Fprincipal extends JFrame implements ActionListener {
    private JLabel lResultado;
    private JLabel lResultado2;
    private JButton jBotones[][] = new JButton[8][8];
    private String matriz[][] = new String[8][8];
    private String jugadorActual = "B";

    public Fprincipal() {
    	super("imagen");
    	this.setSize(1,1);
    	this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Othello POO");
        this.setSize(800,800);
        this.setLayout(new BorderLayout());
        
        JLabel label =new JLabel("",JLabel.CENTER);
        label.setIcon(new ImageIcon("media/ficha negra.png"));
        label.setIcon(new ImageIcon("media/ficha blanca.jpg"));
        this.add(label);
        this.add(new JLabel("imagen de prueba"));
        
        // Etiquetas de resultado
        this.lResultado = new JLabel("0", SwingConstants.LEFT);
        this.lResultado.setFont(new Font("Arial", Font.BOLD, 80));
        this.lResultado.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.lResultado.setVerticalAlignment(SwingConstants.CENTER);

        this.lResultado2 = new JLabel("0", SwingConstants.RIGHT);
        this.lResultado2.setFont(new Font("Arial", Font.BOLD, 80));
        this.lResultado2.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.lResultado2.setVerticalAlignment(SwingConstants.CENTER);

        // Panel para las etiquetas de resultado
        JPanel pEtiquetas = new JPanel(new BorderLayout());
        pEtiquetas.add(lResultado, BorderLayout.WEST);
        pEtiquetas.add(lResultado2, BorderLayout.EAST);
        this.add(pEtiquetas, BorderLayout.NORTH);

        // Panel para los botones
        JPanel pBotones = new JPanel();
        pBotones.setLayout(new GridLayout(8, 8));
        this.add(pBotones, BorderLayout.CENTER);

        // Inicializar la matriz y configurar botones con imágenes
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.matriz[i][j] = "";
                this.jBotones[i][j] = new JButton();
                pBotones.add(this.jBotones[i][j]);
                this.jBotones[i][j].setBackground(Color.GREEN);
                this.jBotones[i][j].addActionListener(this);
            }
        }

        // Inicializar el juego con cuatro fichas en el centro
        matriz[3][3] = "N";
        matriz[3][4] = "B";
        matriz[4][3] = "B";
        matriz[4][4] = "N";

        // Actualizar la interfaz gráfica
        actualizarBotones();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botonClic = (JButton) e.getSource();
        int fila = -1;
        int columna = -1;

        // Encontrar la posición del botón en la matriz
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (botonClic == this.jBotones[i][j]) {
                    fila = i;
                    columna = j;
                    break;
                }
            }
        }

        // Realizar la lógica de movimiento aquí
        if (fila != -1 && columna != -1) {
            // Verificar si la posición está vacía y es un movimiento válido
            if (matriz[fila][columna].isEmpty() && esMovimientoValido(fila, columna)) {
                // Realizar el movimiento y actualizar la matriz
                matriz[fila][columna] = jugadorActual;

                // Invertir las fichas según las reglas del juego
                invertirFichas(fila, columna);

                // Cambiar al siguiente jugador
                jugadorActual = (jugadorActual.equals("B")) ? "N" : "B";

                // Actualizar la interfaz gráfica
                actualizarBotones();
            }
        }
    }

    private boolean esMovimientoValido(int fila, int columna) {
        // Lógica para determinar si el movimiento es válido
        // Implementa tus propias reglas aquí

        return false;
    }

    private void invertirFichas(int fila, int columna) {
        // Lógica para invertir las fichas según las reglas del juego
        // Implementa tus propias reglas aquí
    }

    private void actualizarBotones() {
        ImageIcon iconoBlanco = new ImageIcon("media/ficha blanca.jpg");
        ImageIcon iconoNegro = new ImageIcon("media/ficha negra.png");

        // Configurar botones con imágenes según la matriz
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

    public static void main(String[] args) {
        Fprincipal fprincipal = new Fprincipal();
        fprincipal.setVisible(true);
    }
}
