package presentacion;

import javax.swing.*;

import logica.Othello;

import java.awt.*;

public class PTablero extends JPanel {

    private static final long serialVersionUID = 1L;
    private String[][] matriz;
	private Othello  othello ;
    private JButton[][] jBotones;

    public PTablero(String[][] matriz, Othello  othello) {
        this.matriz = matriz;
        this.othello = othello;
        this.setLayout(new GridLayout(8, 8));
        this.setBackground(Color.GREEN);

      
        
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.addActionListener(othello);
                this.othello.jBotones[i][j] = button;
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
        ImageIcon iconoBlanco = new ImageIcon("media/ficha blanca.png");
        ImageIcon iconoNegro = new ImageIcon("media/ficha negra.png");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (jBotones[i][j] != null) {  
                    if (matriz[i][j] != null && matriz[i][j].equals("B")) {
                        jBotones[i][j].setIcon(iconoNegro);
                    } else if (matriz[i][j] != null && matriz[i][j].equals("N")) {
                        jBotones[i][j].setIcon(iconoBlanco);
                    } else {
                        jBotones[i][j].setIcon(null);
                    }
                }
            }
        }
    }
 
    public void inicializarTablero(String[][] nuevaMatriz) {
        this.matriz = nuevaMatriz;
        actualizarBotones(); 
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

	public void actualizarTablero() {
		// TODO Auto-generated method stub
		
	}
}